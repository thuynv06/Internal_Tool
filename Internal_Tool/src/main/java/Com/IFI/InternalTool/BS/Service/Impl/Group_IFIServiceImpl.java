package Com.IFI.InternalTool.BS.Service.Impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Com.IFI.InternalTool.BS.Service.Group_IFIService;
import Com.IFI.InternalTool.DS.DAO.Group_IFIDAO;
import Com.IFI.InternalTool.DS.Model.Group_IFI;
import Com.IFI.InternalTool.Payloads.GroupRequest;
import Com.IFI.InternalTool.Payloads.PagedResponse;
//import Com.IFI.InternalTool.Utils.ModelMapper;

@Service
public class Group_IFIServiceImpl implements Group_IFIService {

	@Autowired
	private Group_IFIDAO groupDAO;

	@Override
	public Group_IFI getGroupById(String group_id) {
		return groupDAO.findGroupById(group_id);
	}

	@Override
	public Group_IFI createGroupIFI(GroupRequest groupRequest) {
		Group_IFI group = new Group_IFI();
		group.setName(groupRequest.getName());
		group.setGroup_id(groupRequest.getGroup_id());
		return groupDAO.save(group);
	}

	@Override
	public void deleteGroupById(String group_id) {
		groupDAO.deleteGroupById(group_id);

	}

	@Override
	public PagedResponse<Group_IFI> getAllGroup() {
		List<Group_IFI> groups = groupDAO.findAll();
		if (groups.size() == 0) {
			return new PagedResponse<>(Collections.emptyList(), false);
		}
		return new PagedResponse<>(groups, true);
	}

	@Override
	public PagedResponse<Group_IFI> findGroupsLikeName(String name) {
		List<Group_IFI> groups = groupDAO.findGroupLikeName(name);
		if (groups.size() == 0) {
			return new PagedResponse<>(Collections.emptyList(), false);
		}
		return new PagedResponse<>(groups, true);
		
	}
	@Override
	@Transactional
	public PagedResponse<Group_IFI> findGroupByName(String name) {
		return new PagedResponse<>(groupDAO.findByname(name), true);
	}

}
