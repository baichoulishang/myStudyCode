package lambdasinaction.chap7;

import io.netty.util.internal.StringUtil;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @date 2020/3/31 20:19
 */
public class GenerateESData {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> index = Arrays.asList("home", "house", "dog", "cat", "human");
        List<String> type = Arrays.asList("He", "created", "a", "large", "number", "of", "popular", "literary", "works,", "occupies", "a", "special",
                "position", "in", "the", "history", "of", "European", "literature,", "has", "been", "hailed as").stream().distinct().collect(Collectors.toList());
        String document = "mathematics  数学\n" +
                "arts  文科\n" +
                "science  理科\n" +
                "history  历史\n" +
                "geometry  几何\n" +
                "geography  地理\n" +
                "geology  地质学\n" +
                "biology  生物\n" +
                "chemistry  化学\n" +
                "physics  物理\n" +
                "literature  文学\n" +
                "sociology  社会学\n" +
                "philosophy  哲学\n" +
                "psychology  心理学\n" +
                "engineering  工程学\n" +
                "medicine  医学\n" +
                "agriculture  农学\n" +
                "astronomy  天文学\n" +
                "economics  经济学\n" +
                "politics  政治学\n" +
                "law  法学\n" +
                "finance  财政学\n" +
                "architecture  建筑学\n" +
                "艺术流派\n" +
                "Byzantine  拜占庭式\n" +
                "Romanesque  罗马式\n" +
                "Gothic  哥特式\n" +
                "Baroque  巴洛克式\n" +
                "classicism  古典主义,古典风格\n" +
                "romanticism  浪漫主义\n" +
                "realism  现实主义\n" +
                "symbolism  象征主义\n" +
                "impressionism  印象主义\n" +
                "expressionism  表现主义\n" +
                "Fauvism  野兽派\n" +
                "abstract art  抽象派, 抽象主义\n" +
                "Cubism  立体派, 立体主义\n" +
                "naturalism  自然主义\n" +
                "existentialism  存在主义\n" +
                "futurism  未来主义 \n" +
                "\n" +
                "文学\n" +
                "author  作者\n" +
                "essay  随笔\n" +
                "reportage  报告文学\n" +
                "criticism  评论\n" +
                "anthology  选集\n" +
                "edition  版\n" +
                "work  作品\n" +
                "masterpiece  杰作\n" +
                "copyright  版权, 著作权\n" +
                "humanities  人文学科\n" +
                "writer  作家\n" +
                "volume  卷\n" +
                "theatre  戏剧\n" +
                "drama  话剧\n" +
                "comedy  喜剧\n" +
                "tragedy  悲剧\n" +
                "farce  滑稽剧\n" +
                "play  剧本\n" +
                "playwright  编剧\n" +
                "performance  演出\n" +
                "act  幕\n" +
                "scene  场\n" +
                "plot  情节\n" +
                "intrigue  错综复杂的剧情\n" +
                "story  故事\n" +
                "episode  逸事\n" +
                "denouement  结局\n" +
                "poetry  诗歌\n" +
                "poet  诗人\n" +
                "poem  诗\n" +
                "ode  颂歌\n" +
                "sonnet  十四行诗\n" +
                "verse  (诗)节\n" +
                "line  (诗)行\n" +
                "rhyme  韵脚,押韵\n" +
                "metrics  韵律学,格律学\n" +
                "prose  散文\n" +
                "novel  小说\n" +
                "biography  自传\n" +
                "allegory  寓言\n" +
                "science fiction  科幻,科学幻想小说\n" +
                "satire  讽刺诗\n" +
                "essay  杂文\n" +
                "composition  学术著作\n" +
                "rhetoric  修辞学\n" +
                "oratory  讲演术\n" +
                "declamation  朗诵技巧\n" +
                "improvisation  即席讲演\n" +
                "criticism  批判主义\n" +
                "critic  批评家\n" +
                "eloquence  文才\n" +
                "lyricism  抒情性 \n" +
                "\n" +
                "绘画雕塑\n" +
                "gallery  画廊,美术馆\n" +
                "salon  沙龙\n" +
                "exhibition  展览\n" +
                "collection  收藏\n" +
                "inspiration  灵感,启发\n" +
                "artist  大师, 艺术家\n" +
                "pigment  颜料\n" +
                "painter  画家\n" +
                "oil painting  油画\n" +
                "watercolor  水彩画\n" +
                "wash  水墨画\n" +
                "engraving  版画\n" +
                "tracing  临摹\n" +
                "sketch  草稿\n" +
                "portrait  画像\n" +
                "model  模特\n" +
                "caricature  漫画\n" +
                "nude  裸体画\n" +
                "profile  轮廓\n" +
                "landscape  风景画\n" +
                "seascape  海景画\n" +
                "frame  画框\n" +
                "chassis  画布绷架\n" +
                "canvas  画布\n" +
                "studio  画室\n" +
                "pinacotheca  画廊,美术馆\n" +
                "sculptor  雕塑学\n" +
                "carving  雕刻\n" +
                "statue  人像\n" +
                "figure  塑像\n" +
                "bronze  铜像 \n" +
                "\n" +
                "音乐\n" +
                "alto  女低音\n" +
                "anthem  圣歌,赞美诗\n" +
                "baritone  男中音\n" +
                "bass  男低音,贝司\n" +
                "bassoon  低音管\n" +
                "brass  铜管乐器\n" +
                "classical music  古典音乐\n" +
                "conductor  指挥\n" +
                "flute  长笛\n" +
                "harmonica  口琴\n" +
                "jazz  爵士乐\n" +
                "opera  歌剧\n" +
                "orchestra  管弦乐队\n" +
                "rhapsody  狂想曲\n" +
                "solo  独奏,独唱\n" +
                "soloist  独唱者\n" +
                "soprano  女高音\n" +
                "symphony  交响乐\n" +
                "tenor  男高音\n" +
                "pop  流行歌曲\n" +
                "rock  摇滚乐 \n" +
                "\n" +
                "服饰\n" +
                "clothes  衣服,服装\n" +
                "clothing  服装\n" +
                "suit  男西装\n" +
                "dress  女服\n" +
                "uniform  制服\n" +
                "coat  外套\n" +
                "jacket  夹克\n" +
                "pocket  衣袋\n" +
                "sleeve  袖子\n" +
                "shirt  衬衫\n" +
                "sweater  毛衣，运动衫\n" +
                "trousers  裤子\n" +
                "belt  腰带\n" +
                "skirt  裙子\n" +
                "slip  衬裙\n" +
                "handkerchief  手帕\n" +
                "shoe  鞋\n" +
                "sole  鞋底\n" +
                "heel  鞋后跟\n" +
                "boot  靴子\n" +
                "glove  手套\n" +
                "tie  领带\n" +
                "cap  无沿帽\n" +
                "hat  大沿帽\n" +
                "cotton  棉花\n" +
                "canvas  帆布\n" +
                "silk  丝\n" +
                "wool  羊毛，毛料\n" +
                "nylon  尼龙\n" +
                "stripe  条纹\n" +
                "veil  面纱\n" +
                "ring  戒指\n" +
                "necklace  项链\n" +
                "perfume  香水\n" +
                "purse  手提包\n" +
                "garment  外衣\n" +
                "cloak  斗篷\n" +
                "muffler  围巾\n" +
                "jeans  牛仔裤\n" +
                "bra  乳罩\n" +
                "stocking  长袜\n" +
                "belt  腰带\n" +
                "sock  短袜\n" +
                "bikini  比基尼泳衣\n" +
                "apron  围裙\n" +
                "slipper  拖鞋\n" +
                "beret  贝蕾帽\n" +
                "linen  麻\n" +
                "pendant  项饰\n" +
                "earring  耳环\n" +
                "lipstick  口红\n" +
                "wig  假发\n" +
                "tissue  面纸\n" +
                "brooch  胸针\n" +
                "shawl  披肩\n" +
                "raincoat  雨衣\n" +
                "button  扣子\n" +
                "collar  领子\n" +
                "wallet  钱包\n" +
                "blouse  女外套,女衬衫 \n" +
                "\n" +
                "职业\n" +
                "actor  男演员\n" +
                "actress  女演员\n" +
                "singer  歌手\n" +
                "dancer  舞蹈家\n" +
                "musician  音乐家\n" +
                "pianist  钢琴家\n" +
                "painter  画家,油漆匠\n" +
                "teacher  教师\n" +
                "professor  教授\n" +
                "headmaster  中小学校长\n" +
                "headmistress  中小学女校长\n" +
                "headteacher  校长\n" +
                "director  导演\n" +
                "editor  编者\n" +
                "writer  作家\n" +
                "reporter  记者\n" +
                "announcer  广播员\n" +
                "journalist  杂志记者\n" +
                "worker  工人\n" +
                "farmer  农夫\n" +
                "fisherman  渔夫\n" +
                "chemist  化学家,药剂师\n" +
                "engineer  工程师\n" +
                "explorer  探险家\n" +
                "researcher  研究员\n" +
                "doctor  医生,博士\n" +
                "nurse  护士\n" +
                "surgeon  外科医生\n" +
                "sailor  水手\n" +
                "seaman  船员\n" +
                "pilot  飞行员,领航员\n" +
                "astronaut  宇航员\n" +
                "driver  驾驶员\n" +
                "athlete  运动员\n" +
                "policeman  警察\n" +
                "detective  侦探\n" +
                "judge  法官\n" +
                "lawyer  律师\n" +
                "attorney  律师\n" +
                "cook  厨子,厨师\n" +
                "baker  面包师\n" +
                "waiter  侍者\n" +
                "waitress  女服务生\n" +
                "butcher  屠夫\n" +
                "clerk  办事员\n" +
                "typist  打字员\n" +
                "secretary  秘书\n" +
                "salesman  售货员,推销员\n" +
                "shopkeeper  零售商,店主\n" +
                "bookseller  书商\n" +
                "tailor  裁缝\n" +
                "soldier  军人\n" +
                "postman  邮差\n" +
                "mailman  邮差\n" +
                "firefighter  消防人员\n" +
                "conductor  乘务员\n" +
                "librarian  图书管理员\n" +
                "baby-sitter  保姆\n" +
                "apprentice  学徒工\n" +
                "artisan  工匠\n" +
                "craftsman  工匠\n" +
                "specialist  专家\n" +
                "employer  雇主,老板\n" +
                "receptionist  接待员\n" +
                "operator  电话接线员\n" +
                "interpreter  翻译\n" +
                "photographer  摄影师\n" +
                "playwright  剧作家\n" +
                "linguist  语言学家\n" +
                "botanist  植物学家\n" +
                "economist  经济学家\n" +
                "chemist  化学家\n" +
                "scientist  科学家\n" +
                "philosopher  哲学家\n" +
                "politician  政治学家\n" +
                "physicist  物理学家\n" +
                "archaeologist  考古学家\n" +
                "geologist  地质学家\n" +
                "mathematician  数学家\n" +
                "biologist  生物学家\n" +
                "zoologist  动物学家\n" +
                "statistician  统计学家\n" +
                "physiologist  生理学家\n" +
                "futurologist  未来学家\n" +
                "artist  艺术家\n" +
                "composer  作曲家\n" +
                "designer  设计家\n" +
                "sculptor  雕刻家\n" +
                "designer  服装设计师\n" +
                "model  模特\n" +
                "poet  诗人\n" +
                "merchant  商人\n" +
                "stewardess  空中小姐\n" +
                "porter  行李夫\n" +
                "architect  建筑师\n" +
                "druggist  药剂师\n" +
                "chemist  药剂师\n" +
                "guide  导游\n" +
                "dentist  牙科医生\n" +
                "supervisor  监工 \n" +
                "\n" +
                "国家和语言\n" +
                "America  美国,美洲\n" +
                "Arab  阿拉伯人\n" +
                "Australia  澳洲,澳大利亚\n" +
                "Austria  奥地利\n" +
                "Britain  英国\n" +
                "Canada  加拿大\n" +
                "China  中国\n" +
                "Egypt  埃及\n" +
                "England  英国\n" +
                "France  法国\n" +
                "Germany  德国\n" +
                "Greece  希腊\n" +
                "Holland  荷兰\n" +
                "India  印度\n" +
                "Ireland  爱尔兰\n" +
                "Italy  意大利\n" +
                "Japan  日本\n" +
                "Spain  西班牙\n" +
                "Sweden  瑞典\n" +
                "Swiss  瑞士人\n" +
                "Switzerland  瑞士\n" +
                "Brazil  巴西\n" +
                "Finland  芬兰\n" +
                "Norway  挪威\n" +
                "Russia  俄国\n" +
                "Belgium  比利时\n" +
                "Arabic  阿拉伯语\n" +
                "Chinese  中国人,汉语\n" +
                "Egyptian  埃及人\n" +
                "English  英语\n" +
                "French  法国人,法文\n" +
                "German  德国人,德语\n" +
                "Greek  希腊人,希腊文\n" +
                "Irish  爱尔兰人,爱尔兰语\n" +
                "Italian  意大利人,意大利语\n" +
                "Japanese  日本人,日文\n" +
                "Spanish  西班牙人,西班牙语\n" +
                "Dutch  荷兰人,荷兰语 \n" +
                "\n" +
                "政治\n" +
                "colony  殖民地\n" +
                "democracy  民主政治\n" +
                "dynasty  朝代\n" +
                "extinction  消灭\n" +
                "feudalism  封建制度\n" +
                "administration  政府\n" +
                "aggression  侵略\n" +
                "ambassador  大使\n" +
                "anarchy  无政府状态\n" +
                "announcement  公告\n" +
                "aristocracy  贵族政治\n" +
                "autonomy  自治\n" +
                "ballot  选票\n" +
                "bill  法案\n" +
                "bloodshed  流血，屠杀\n" +
                "bureaucracy  官僚政治\n" +
                "candidate  候选人\n" +
                "cession  割让（土地）\n" +
                "commotion  骚动,暴乱\n" +
                "community  社区,\n" +
                "compact  协议\n" +
                "congress  国会\n" +
                "congressman  国会议员\n" +
                "conspiracy  阴谋\n" +
                "conspirator  同谋者\n" +
                "constitution  宪法\n" +
                "consul  领事\n" +
                "convention  惯例\n" +
                "cooperation  合作\n" +
                "custom  习惯,风俗,海关\n" +
                "delegate  代表\n" +
                "delegation  代表团\n" +
                "demonstration  示威\n" +
                "deposition  免职\n" +
                "despot  暴君\n" +
                "dictator  独裁者\n" +
                "dictatorship  独裁政权\n" +
                "diplomacy  外交政策\n" +
                "diplomat  外交官\n" +
                "discrimination  岐视\n" +
                "dominion  领土,主权,统治\n" +
                "embassy  大使馆\n" +
                "emperor  皇帝\n" +
                "empire  帝国\n" +
                "ferment  动乱\n" +
                "government  政府,内阁\n" +
                "governor  统治者\n" +
                "hegemony  霸权\n" +
                "ideology  意识形态\n" +
                "kingdom  王国\n" +
                "legion  军团\n" +
                "monarch  帝王\n" +
                "monarchy  君主政体\n" +
                "motion  动议\n" +
                "movement  运动\n" +
                "nationality  国籍\n" +
                "oligarchy  寡头政治\n" +
                "pact  协定,条约\n" +
                "petition  请愿\n" +
                "philanthropy  博爱,仁慈\n" +
                "policy  政策,方针\n" +
                "poll  民意测验";
        List<String> documents = Arrays.stream(document.split("\n"))
                .filter(e -> !StringUtil.isNullOrEmpty(e))
                .distinct()
                .collect(Collectors.toList());
        int size = documents.size();
        Random random = new Random();
        String es = "";
        PrintWriter out = new PrintWriter("C:\\Users\\Chocolate\\Desktop\\request.json");
        for (int i = 0; i < 1000; i++) {
            es = "{\"index\":{\"_index\":\"" + index.get(random.nextInt(index.size())) + "\",\"_type\":\"" + type.get(random.nextInt(type.size())) + "\",\"_id\":\"" + i + "\"}}\n" +
                    "{\"id\": " + i + ", \"location\": \"" + documents.get(i % size) + "\", \"money\": " + random.nextInt(1000000) + ", \"area\":" + random.nextInt(1000000) + ", \"type\": \""
                    + documents.get(i % size) + "\", \"style\": \"" + documents.get(i % size) + "\"}\n";
            out.write(es);
        }
    }
}
