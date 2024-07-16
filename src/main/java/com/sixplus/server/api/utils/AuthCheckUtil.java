package com.sixplus.server.api.utils;


import com.sixplus.server.api.core.constant.ComConstantAuth;

public class AuthCheckUtil {

	public void main(String[] args) {
//	public static void main(String[] args) {
//		/* 읽기 권한 체크 */
//		SELECT
//		'R' AS ahtuType,
//		sum(IFNULL(stdAuth.permit,0))
//		FROM (
//		   /* 할당된 권한 */
//			SELECT 'AUTH_R_OWN' AS auth_id
//			UNION SELECT 'AUTH_R_CORP'
//			UNION SELECT 'AUTH_R_ALL'
//		) assingAuth
//		LEFT OUTER JOIN (
//		   /* 권한연산을 위한 기준 */
//			SELECT 'AUTH_R_OWN' AS auth_id, 1 AS permit
//			UNION SELECT 'AUTH_R_DEPT', 2
//			UNION SELECT 'AUTH_R_CHILD_DEPT', 4
//			UNION SELECT 'AUTH_R_CORP', 8
//			UNION SELECT 'AUTH_R_ALL', 16
//		) stdAuth ON assingAuth.AUTH_ID = stdAuth.AUTH_ID
//		UNION
//		/* 쓰기/싫행 권한 체크 */
//		SELECT
//		'W' AS ahtuType,
//		sum(IFNULL(stdAuth.permit,0))
//		FROM (
//		   /* 할당된 권한 */
//			SELECT 'AUTH_W_OWN' AS auth_id
//			UNION SELECT 'AUTH_W_CORP'
//			UNION SELECT 'AUTH_W_ALL'
//		) assingAuth
//		LEFT OUTER JOIN (
//		   /* 권한연산을 위한 기준 */
//			SELECT 'AUTH_W_OWN' AS auth_id, 1 AS permit
//			UNION SELECT 'AUTH_W_DEPT', 2
//			UNION SELECT 'AUTH_W_CHILD_DEPT', 4
//			UNION SELECT 'AUTH_W_CORP', 8
//			UNION SELECT 'AUTH_W_ALL', 16
//		) stdAuth ON assingAuth.AUTH_ID = stdAuth.AUTH_ID
//		UNION
//		/* 삭제 권한 체크 */
//		SELECT
//		'D' AS ahtuType,
//		sum(IFNULL(stdAuth.permit,0))
//		FROM (
//		   /* 할당된 권한 */
//			SELECT 'AUTH_D_OWN' AS auth_id
//			UNION SELECT 'AUTH_D_CORP'
//			UNION SELECT 'AUTH_D_ALL'
//		) assingAuth
//		LEFT OUTER JOIN (
//		   /* 권한연산을 위한 기준 */
//			SELECT 'AUTH_D_OWN' AS auth_id, 1 AS permit
//			UNION SELECT 'AUTH_D_DEPT', 2
//			UNION SELECT 'AUTH_D_CHILD_DEPT', 4
//			UNION SELECT 'AUTH_D_CORP', 8
//			UNION SELECT 'AUTH_D_ALL', 16
//		) stdAuth ON assingAuth.AUTH_ID = stdAuth.AUTH_ID

		/*
		아래는 필수 기본 권한이며, 상하관계가 존재한다.
		서버 및 화면에서 필수로 적용되어야 한다.
		아래 권한명은 프로그램에 그대로 적용되므로 변경불가하다.
		기타 특별 권한은 별도로 추가하여 작성한다.
		-----------------------------------------------------------
		AUTH_R_OWN , 소유자-읽기 , AUTH_DEPT_R
		AUTH_R_DEPT , 부서-읽기 , AUTH_CHILD_DEPT_R
		AUTH_R_CHILD_DEPT , 하위부서-읽기 , AUTH_CORP_R
		AUTH_R_CORP , 회사-읽기 , AUTH_ALL_R
		AUTH_R_ALL , 모두-읽기 , NULL
		
		AUTH_W_OWN , 소유자-쓰기 , AUTH_DEPT_W
		AUTH_W_DEPT , 부서-쓰기 , AUTH_CHILD_DEPT_W
		AUTH_W_CHILD_DEPT , 하위부서-쓰기 , AUTH_CORP_W
		AUTH_W_CORP , 회사-쓰기 , AUTH_ALL_W
		AUTH_W_ALL , 모두-쓰기 , NULL
		
		AUTH_D_OWN , 소유자-삭제 , AUTH_DEPT_D
		AUTH_D_DEPT , 부서-삭제 , AUTH_CHILD_DEPT_D
		AUTH_D_CHILD_DEPT , 하위부서-삭제 , AUTH_CORP_D
		AUTH_D_CORP , 회사-삭제 , AUTH_ALL_D
		AUTH_D_ALL , 모두-삭제 , NULL
		-----------------------------------------------------------
		(처리 예시)
		읽기권한 1 2 4 8 16 , 쓰기권한 1 2 4 8 16, 삭제권한 1 2 4 8 16
		
		if(읽기권한 < 1){
		 // 권한 없음
		}else if(읽기권한 == 1){
		 // 소유자-읽기
		}else if(읽기권한 > 1 && 읽기권한 <= 3){
		 // 소유자-읽기, 그룹-읽기
		}else if(읽기권한 > 3 && 읽기권한 <= 7){
		 // 소유자-읽기, 그룹-읽기, 하위그룹-읽기
		}else if(읽기권한 > 7 && 읽기권한 <= 15){
		 // 소유자-읽기, 그룹-읽기, 하위그룹-읽기, 회사전체-읽기
		}else if(읽기권한 > 15){
		 // 전체 읽기
		}
		*/

		Integer authPermitNum[] = new Integer[3];
		authPermitNum[0] = 1; // R
		authPermitNum[1] = 9; // W
		authPermitNum[2] = 25; // D

		String authPermitTypeResut[] = new String[3];

		for (int i = 0; i < authPermitNum.length; i++) {
			if (authPermitNum[i] < 1) {
				// 권한 없음
				authPermitTypeResut[i] = null;
			} else if (authPermitNum[i] == 1) {
				// 소유자-읽기
				authPermitTypeResut[i] = ComConstantAuth.AUTH_TYPE_OWN;
			} else if (authPermitNum[i] > 1 && authPermitNum[i] <= 3) {
				// 소유자-읽기, 그룹-읽기
				authPermitTypeResut[i] = ComConstantAuth.AUTH_TYPE_GROUP;
			} else if (authPermitNum[i] > 3 && authPermitNum[i] <= 7) {
				// 소유자-읽기, 그룹-읽기, 하위그룹-읽기
				authPermitTypeResut[i] = ComConstantAuth.AUTH_TYPE_SUB_GROUP;
			} else if (authPermitNum[i] > 7 && authPermitNum[i] <= 15) {
				// 소유자-읽기, 그룹-읽기, 하위그룹-읽기, 회사전체-읽기
				authPermitTypeResut[i] = ComConstantAuth.AUTH_TYPE_CORP;
			} else if (authPermitNum[i] > 15) {
				// 전체 읽기
				authPermitTypeResut[i] = ComConstantAuth.AUTH_TYPE_ALL;
			}
		}

		for (int i = 0; i < authPermitTypeResut.length; i++) {
			System.out.println(authPermitTypeResut[i]);
			/*
			 R_OWN 
			 R_GROUP     
			 R_SUB_GROUP 
			 R_CORP      
			 R_ALL       
			 W_OWN 
			 W_GROUP     
			 W_SUB_GROUP 
			 W_CORP      
			 W_ALL       
			 D_OWN 
			 D_GROUP     
			 D_SUB_GROUP 
			 D_CORP      
			 D_ALL       
			 */
		}

	}
}
