package gateway.wrb.services;

import java.util.List;

import gateway.wrb.domain.ER001Info;
import gateway.wrb.domain.FbkFilesInfo;

public interface ER001Service {
	List<ER001Info> getAllER001();

	ER001Info getER001(long id);

	void importER001(FbkFilesInfo fbkFilesInfo);

	void updateER001(ER001Info rv001Info);

	void deleteER001(long id);

	boolean isER001exist(ER001Info rv001Info);
}
