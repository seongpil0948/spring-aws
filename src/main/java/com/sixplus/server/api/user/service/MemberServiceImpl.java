package com.sixplus.server.api.user.service;
import com.sixplus.server.api.core.config.security.JwtAuthenticationProvider;
import com.sixplus.server.api.core.config.security.JwtTokenProvider;
import com.sixplus.server.api.core.exception.ShopErrorException;
import com.sixplus.server.api.model.Response;
import com.sixplus.server.api.user.model.LoginVO;
import com.sixplus.server.api.user.model.MemberVo;
import com.sixplus.server.api.user.model.UserEntity;
import com.sixplus.server.api.user.repository.UserRepository;
import com.sixplus.server.api.utils.CmmCode;
import com.sixplus.server.api.utils.CmmUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
* <pre>
* 1. 패키지명 : com.dw.ids.sonic.userfe.service
* 2. 타입명 : MemberService.java
* 3. 작성일 : 2023. 11. 10
* 4. 작성자 : idsTrust
* 5. 설명   : MEMBER - Service Interface 
* Copyright ids.trust corp. all right reserved
* </pre>
**/
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private final JwtAuthenticationProvider authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
//    private final RedisFeignClient redisFeignClient;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
	public Response<?> getUserInfoByUserId(LoginVO vo) {
		String accessToken;
        String refreshToken;
        Map<String, Object> dataMap = new HashMap<>();

        try {
            //
            MemberVo jwtUserInfo = new MemberVo();
            jwtUserInfo.setUid(vo.getLoginId());
            jwtUserInfo.setRoles(vo.getRoles());

            // Login_id 인증처리 사용
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtUserInfo, vo.getLoginPwd()));

            // SecurityContext Holder 저장
            SecurityContext securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(authenticate);
            SecurityContextHolder.setContext(securityContext);

            if (ObjectUtils.isNotEmpty(authenticate.getPrincipal())) {
                MemberVo userInfo = this.regenerateJwtUserInfo(vo, jwtUserInfo);
            	
            	if (ObjectUtils.isEmpty(userInfo)) {
					throw new ShopErrorException(CmmCode.SHOP_MEMBER_6300.getCode());
            	}else {
            		// TODO
					accessToken = jwtTokenProvider.createToken(userInfo);
                    refreshToken = jwtTokenProvider.createRefreshToken(userInfo);

                    try {
                        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

                        // Redis 서버가 shutdown되도 넘어간다.
//                        redisFeignClient.setValue(RedisShopVO.of("TK:"+activeProfile+":U:"+userInfo.getUserId(), refreshToken, jwtTokenProvider.getRefreshValidityInMilliseconds() / 1000));
                        valueOperations.set(CmmUtils.getRedisUserKey(activeProfile, userInfo.getUid()), refreshToken, Duration.ofMillis(jwtTokenProvider.getRefreshValidityInMilliseconds()));
                    } catch(Exception e) {
                        log.error("Redis Server 이상 발생 {}", e.getMessage());
                    }

					userInfo.setPw(null);
                    dataMap.put("accessToken", accessToken);
                    dataMap.put("dataMap", userInfo);

            	}
            } else {
				throw new ShopErrorException(CmmCode.SHOP_MEMBER_6300.getCode());
            }
        } catch (UsernameNotFoundException ue) {
            log.warn("UsernameNotFoundException {}", ue.getMessage());
            throw new ShopErrorException(CmmCode.SHOP_LOGIN_1000.getCode());
        } catch (BadCredentialsException be) {
            log.warn("BadCredentialsException {}", be.getMessage());
            throw new ShopErrorException(CmmCode.SHOP_LOGIN_1001.getCode());
        } catch (ShopErrorException se) {
            log.warn("ShopErrorException {}", se.getMessage());
            throw se;
        } catch (Exception e) {
            log.error("login failed : " + e.getMessage());
            throw new ShopErrorException(CmmCode.SHOP_ERROR_9999.getCode());
        }

        return Response.ok(dataMap);
	}

    public MemberVo regenerateJwtUserInfo(LoginVO vo, MemberVo jwtUserInfo)  {
        Optional<UserEntity> user = userRepository.findById(vo.getLoginId());
        if (user.isEmpty()) {
            throw new ShopErrorException(CmmCode.SHOP_LOGIN_1000.getCode());
        }

        MemberVo userInfo = new MemberVo();
        userInfo.setRoles(user.get().getRoles());
        userInfo.setPw(null);
        userInfo.setUid(vo.getLoginId());

        return userInfo;
    }


}