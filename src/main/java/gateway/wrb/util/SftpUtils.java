package gateway.wrb.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import gateway.wrb.controllers.FbkController;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SftpUtils {
    public static final Logger logger = LoggerFactory.getLogger(FbkController.class);

    public void getFilesSftp(String SFTPHOST, String SFTPPORT, String SFTPUSER, String SFTPPASS, String SFTPWORKINGDIR, String SFTPREMOTE) throws IOException {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        SSHClient sshClient = new SSHClient();
        try {
            System.out.println("=============== Starting GET Files SFTP : ");
            sshClient = setupSshj(SFTPHOST, SFTPUSER, SFTPPASS);
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.get(SFTPREMOTE, new FileSystemFile(SFTPWORKINGDIR));
            } finally {
                sftpClient.close();
            }
            System.out.println("=============== End GET Files SFTP : " + sftpClient);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("SFTP : " + ex.getMessage());
        } finally {
            sshClient.disconnect();
        }
    }

    public void putFileSftp(String SFTPHOST, String SFTPPORT, String SFTPUSER, String SFTPPASS, File file, String SFTPREMOTE) {
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        SSHClient sshClient = new SSHClient();
        try {
            System.out.println("=============== Starting PUT Files SFTP : ");
            sshClient = setupSshj(SFTPHOST, SFTPUSER, SFTPPASS);
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.put(new FileSystemFile(file), SFTPREMOTE);
            } finally {
                sftpClient.close();
            }
            System.out.println("=============== End PUT Files SFTP : " + sftpClient);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("SFTP : " + ex.getMessage());
        }
    }

    private SSHClient setupSshj(String SFTPHOST, String SFTPUSER, String SFTPPASS) throws IOException {
        SSHClient client = new SSHClient();
        try {
            client.addHostKeyVerifier(new PromiscuousVerifier());
            client.connect(SFTPHOST);
            client.authPassword(SFTPUSER, SFTPPASS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.disconnect();
        }
        return client;
    }
}
