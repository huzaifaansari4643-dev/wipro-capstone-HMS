//package com.hms.auth.dto;
//
//
//
//import com.hms.auth.enums.UserRole;
//
//import lombok.Data;
//
//@Data
//public class AuthenticationResponse {
//	
//	private String jwt;
//	private Long userId;
//	private UserRole userRole;
//	
//
//}
package com.hms.auth.dto;

import com.hms.auth.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private Long userId;
    private String name;
    private String email;
    private UserRole userRole;
}