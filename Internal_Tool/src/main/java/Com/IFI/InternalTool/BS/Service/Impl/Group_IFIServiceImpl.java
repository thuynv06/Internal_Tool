package Com.IFI.InternalTool.BS.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Com.IFI.InternalTool.BS.Service.Group_IFIService;
import Com.IFI.InternalTool.DS.DAO.Group_IFIDAO;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;

@Service
public class Group_IFIServiceImpl implements Group_IFIService {

	@Autowired
	private Group_IFIDAO groupDAO;

	@Override
	public Group_IFI getGroupById(long id) {
		return groupDAO.findById(id).get();
	}

	@Override
	public Group_IFI createGroupIFI(GroupRequest groupRequest) {
		Group_IFI group=new Group_IFI();
		group.setName(groupRequest.getName());
		group.setGroup_id(group_id);
		return groupDAO.save(group);
	}

	@Override
	public void deleteGroup(long id) {
		groupDAO.deleteById(id);

	}

}
