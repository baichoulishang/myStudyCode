package lambdasinaction.chap7;

/**
 * @date 2020/3/31 20:15
 */

/**
 * ElasticSearch 常量
 * ACTION_* : bulk api json key
 * ES_* : ElasticSearch 中常见属性
 *
 * @author Taven
 */
public class ESConstant {

    /**
     * bulk api json key 当文档不存在时创建之
     */
    public static final String ACTION_CREATE = "create";

    /**
     * bulk api json key 创建新文档或替换已有文档
     */
    public static final String ACTION_INDEX = "index";

    /**
     * bulk api json key 局部更新文档
     */
    public static final String ACTION_UPDATE = "update";

    /**
     * bulk api json key 删除一个文档
     */
    public static final String ACTION_DELETE = "delete";

    /**
     * ES中的索引
     */
    public static final String ES_INDEX = "_index";

    /**
     * ES中的类型
     */
    public static final String ES_TYPE = "_type";

    /**
     * ES中的id
     */
    public static final String ES_ID = "_id";

}
