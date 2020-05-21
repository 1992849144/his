package org.java.auth.feign;


import org.java.his.managesuser.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("managesuser-service")
public interface UserClient extends UserApi {
}
