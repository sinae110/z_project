package annotation.maps;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;


public interface TestMappable {

	/* XML 방식 */
	public List<?> selectTest();

	/* 애노테이션 방식 */

	// 5. 등록테스트
	@Insert(value = "{ CALL  P_INSERT_SPR(#{s_name, mode=IN, jdbcType=VARCHAR}, #{s_age, mode=IN, jdbcType=INTEGER})}")
	@Options(statementType = StatementType.CALLABLE)
	public void doP_INSERT_SPR(HashMap<String, Object> m);
	// 4. 삭제테스트

	// 3. 수정테스트
	@Select("UPDATE SPR_TEST2 SET S_NAME = #{s_name}	WHERE S_NUM = #{snum}")
	public HashMap<String, String> setUpdate(HashMap<String, Object> m);

	// 2. 매개변수 있는 SELECT
	@Select("SELECT * FROM SPR_TEST2 WHERE S_NUM > #{snum} ORDER BY S_NUM DESC")
	public List<HashMap<String, String>> getListWhere(int snum);

	// 1. 매개변수 없는 SELECT
	@Select("SELECT * FROM SPR_TEST2 ORDER BY S_NUM DESC")
	public List<HashMap<String, String>> getList();

	// 1. 매개변수 없는 SELECT
	@Select("SELECT * FROM spr_test2 ORDER BY S_NUM DESC")
	public List<HashMap<String, String>> getList2();

}
