package com.sixplus.server.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * ModelMapper를 쓰는 이유
 * MSA(Micro Service Architecture)에서는 서로다른 도메인 간에 API를 이용해서 통신을 하게 된다. API 콜을 하게 되면 데이터가 DTO에 담겨서 온다. 그런데 API가 제공하는 DTO를 그대로 사용하면 안된다. 왜냐하면 API는 언제든지 변경될 수 있기 때문이다. API가 제공하는 DTO를 그대로 사용해서 어플리케이션을 작성하면 나중에 API가 변경됬을 때 모든 코드를 다 바꿔줘야 한다. 따라서 API DTO를 이름만 바꿔서(동일한 데이터 필드를 가짐) WrapDTO로 변환해서 사용한다. 그리고 Store 역할을 하는 객체가 API콜과 DTO<-> WrapDTO간의 변환을 담당해서 API콜이라는 책임을 Store라는 곳에 집약시킨다. 이렇게하면 API가 변경되어도 해당 책임을 담당하는 Store만 변경하면 된다.
 * 이때, Store에서 DTO<->WrapDTO를 해줘야하는데, 필드가 한 두개면 그냥 setter로 넣어도 되지만, 보통 엔터프라이즈 급에서는 필드가 수십개다. 그래서 간단한 매핑의 경우 ModelMapper라는 매핑을 도와주는 클래스를 사용하게 된다.
 * Source --> Destination 오브젝트의 변환을 도와주는 것이 모델매퍼이다
 * 하지만, 필드가 복잡한 경우도 있다. 예를들어 Map<String, Map<A>>이고 A안에 또 다른 객체가 있을 경우 끝도 없다. 이럴경우 모델매퍼를 Customizing해서 사용하는 방법이 있고, Builder패턴과 같은 객체 생성 패턴을 써서 일일이 셋팅해주는 방법이 있다.
 *
 * 기본 convert이외에도 이런식으로 custom 객체 변경이 가능 (밑에 처럼 간단한거 말고 복잡한거에만 쓰자..)
 * ModelMapper mapper = ModelMapperUtils.standardMapper();
 * mapper.typeMap(DT_SDIF00139_THESHOP_responseT_RETURN.class, SapStockVO.class).addMappings(m -> {
 *     m.map(DT_SDIF00139_THESHOP_responseT_RETURN::getBUKRS, SapStockVO::setBukrs);
 *     m.map(DT_SDIF00139_THESHOP_responseT_RETURN::getWERKS, SapStockVO::setWerks);
 * });
 * SapStockVO sap = mapper.map(f, SapStockVO.class);
 *
 * 리스트 변경 시
 * 변경 전:
 * List<BatchSellerInfo> orderGoodsList = (List<BatchSellerInfo>) orderMap.get("orderGoodsList");
 * 변경 후"
 * List<BatchSellerInfo> orderGoodsList = modelMapper.map(orderMap.get("orderGoodsList"), new TypeToken<List<BatchSellerInfo>>() {}.getType());
 *
 */
public class ModelMapperUtils {
    public static ModelMapper standardMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        modelMapper.getConfiguration().setSkipNullEnabled(true); // 기본 매칭 전력을 사용하면 null값까지 함게 업데이트 되므로 true
        return modelMapper;
    }

    public static ModelMapper looseMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setSkipNullEnabled(true); // 기본 매칭 전력을 사용하면 null값까지 함게 업데이트 되므로 true
        return modelMapper;
    }

    public static ModelMapper strictMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // setAmbiguityIgnored: 둘 이상의 소스 속성과 일치하는 대상 속성을 무시
        modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * 일반 model 변경 (간소화 버전)
     *
     * @param obj
     * @return
     * @param <D>
     */
    public static <D> D convert(Object obj, Class<D> clz) {
        ModelMapper modelMapper = strictMapper();
        return modelMapper.map(obj, clz);
    }

    /**
     * list 변경
     *
     * @param obj
     * @return
     * @param <D>
     */
    public static <D> List<D> convertList(Object obj) {
        ModelMapper modelMapper = standardMapper();
        return modelMapper.map(obj, new TypeToken<List<D>>() {}.getType());
    }

    /**
     * list 변경 (LinkedHashmap으로 될 떄 강제로 변경)
     * @param obj
     * @param claz
     * @return
     * @param <D>
     */
    public static <D> List<D> convertList(Object obj, Class<D> claz) {
        ModelMapper modelMapper = strictMapper();
        List<D> list = modelMapper.map(obj, new TypeToken<List<D>>() {}.getType());
        return list.stream().map(m -> modelMapper.map(m, claz)).collect(Collectors.toList());
    }

    /**
     * map 변경
     *
     * @param obj
     * @return
     * @param <V>
     */
    public static <V> Map<String, V> convertMap(Object obj) {
        ModelMapper modelMapper = standardMapper();

        // String형태는 깔끔하게 포기
        if(obj instanceof String) {
            ObjectMapper ob = new ObjectMapper();
            try {
                return ob.readValue(obj.toString(), new TypeReference<Map<String, V>>() {});
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return modelMapper.map(obj, new TypeToken<HashMap<String, V>>() {}.getType());
    }

    /**
     * Model(camelCase) -> Map(underscore)
     *
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> convertMapToUnderScore(Object obj) {
        ObjectMapper ob = new ObjectMapper();
        ob.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return ob.convertValue(obj,  new TypeReference<Map<String, Object>>() {});
    }
}
