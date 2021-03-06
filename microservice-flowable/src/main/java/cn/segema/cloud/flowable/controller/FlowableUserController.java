package cn.segema.cloud.flowable.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.segema.cloud.flowable.domain.FlowableUser;
import cn.segema.cloud.flowable.repository.FlowableUserRepository;

@RestController
public class FlowableUserController {
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private FlowableUserRepository activitiUserRepository;

  /**
   * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
   * @RequestMapping(value = "/id", method = RequestMethod.GET)
   * 类似的注解还有@PostMapping等等
   * @param id
   * @return user信息
   */
  @GetMapping("/{userId}")
  public FlowableUser findById(@PathVariable Integer userId) {
    FlowableUser findOne = this.activitiUserRepository.findOne(userId);
    return findOne;
  }
  
  @GetMapping("/list")
	public List<FlowableUser> list(FlowableUser user, Model model) {
		List<FlowableUser> userList = activitiUserRepository.findAll();
		return userList;
	}

	@PostMapping("/add")
	public FlowableUser add(FlowableUser user, Model model) {
		activitiUserRepository.save(user);
		return user;
	}

	@RequestMapping(value = "edit")
	public FlowableUser edit(FlowableUser user, Model model) {
		// Role oldRole = roleRepository.getOne(role.getRoleId());
		// BeanUtils.copyProperties(role, oldRole);
		activitiUserRepository.save(user);
		return user;
	}

	@RequestMapping(value = "delete")
	public FlowableUser delete(FlowableUser user) {
		activitiUserRepository.delete(user);
		return user;
	}
  
  
/*  @GetMapping("/listByUserName/{userName}") 
  public List<UserPersonalVO> listByUserName(@PathVariable String userName) {
	  List<UserPersonalVO> userList = userRepository.findByUserName(userName);
	  return userList;
	}*/
  
  
  @GetMapping("/listByPage/{page}/{size}")
	public Page<FlowableUser> listByPage(@PathVariable Integer page,@PathVariable Integer size) {
		Sort sort = new Sort(Direction.DESC, "contractId");
		Pageable pageable = new PageRequest(page, size, sort);
		return activitiUserRepository.findAll(pageable);
	}

  /**
   * 本地服务实例的信息
   * @return
   */
  @GetMapping("/instance-info")
  public ServiceInstance showInfo() {
    ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
    return localServiceInstance;
  }
}
