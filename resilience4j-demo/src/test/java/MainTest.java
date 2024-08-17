import com.aquarius.wizard.common.utils.JSONUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author zhaoyijie
 * @since 2024/7/29 12:34
 */
public class MainTest {

    @Test
    public void test1() throws IOException {
        String fileName = "左手指月 (电视剧《香蜜沉沉烬如霜》片尾曲) - 萨顶顶";

        String json = "{\n" +
                "    \"code\": 200,\n" +
                "    \"data\": {\n" +
                "        \"lrc\": \"[00:01.00]歌曲名 左手指月(电视剧《香蜜沉沉烬如霜》片尾)\\r\\n[00:02.00]歌手名 萨顶顶\\r\\n[00:03.00]作词：喻江\\r\\n[00:04.00]作曲：萨顶顶\\r\\n[00:05.35]制作人：常石磊\\r\\n[00:06.25]编曲：刘胡轶常石磊\\r\\n[00:07.20]弦乐编写：刘胡轶\\r\\n[00:08.40]铜管\\/弦乐：国际首席爱乐乐团\\r\\n[00:09.35]铜管\\/弦乐录音师：李巍\\r\\n[00:10.25]铜管\\/弦乐录音棚：中国剧院录音棚\\r\\n[00:11.60]和声：萨顶顶\\r\\n[00:12.55]人声录音师：李杨\\/曹洋\\r\\n[00:13.50]人声录音棚： 55Tec Studio\\/C-VOICE STUDIO \\r\\n[00:14.40]人声编辑：曹洋\\r\\n[00:15.50]混音师： 赵靖\\r\\n[00:16.65]混音录音棚： BIG J Studio \\r\\n[00:17.50]音乐制作出品：中英音乐\\r\\n[00:18.65]监制：付宏声\\r\\n[00:19.50]音乐发行：智慧大狗×天才联盟\\r\\n[00:20.60]统筹：张葛王明宇\\r\\n[00:25.10]左手握大地右手握着天\\r\\n[00:31.80]掌纹裂出了十方的闪电\\r\\n[00:37.45]把时光匆匆兑换成了年\\r\\n[00:43.95]三千世如所不见\\r\\n[00:51.35]左手拈着花右手舞着剑\\r\\n[00:57.90]眉间落下了一万年的雪\\r\\n[01:03.85]一滴泪啊啊啊\\r\\n[01:10.65]那是我啊啊啊\\r\\n[01:44.02]左手一弹指右手弹着弦\\r\\n[01:50.57]舟楫摆渡在忘川的水间\\r\\n[01:56.42]当烦恼能开出一朵红莲\\r\\n[02:02.97]莫停歇给我杂念\\r\\n[02:10.42]左手指着月右手取红线\\r\\n[02:16.62]赐予你和我如愿的情缘\\r\\n[02:22.72]月光中啊啊啊\\r\\n[02:29.17]你和我啊啊啊\\r\\n[02:49.88]左手化成羽右手成鳞片\\r\\n[02:56.33]某世在云上某世在林间\\r\\n[03:02.28]愿随你用一粒微尘的模样\\r\\n[03:08.73]在所有尘世浮现\\r\\n[03:16.03]我左手拿起你右手放下你\\r\\n[03:22.58]合掌时你全部被收回心间\\r\\n[03:28.48]一炷香啊啊啊\\r\\n[03:34.93]你是我无二无别\\r\\n\",\n" +
                "        \"tlyric\": null\n" +
                "    },\n" +
                "    \"msg\": \"\"\n" +
                "}";

        ObjectNode objectNode = JSONUtils.parseObject(json);
        JsonNode dataNode = objectNode.get("data");
        JsonNode lrcNode = dataNode.get("lrc");
        String basePath = "/Users/zhaoyijie/Music/";
        File file = new File(basePath + fileName + ".lrc");
        String text = lrcNode.asText();
        String outPutString;
        if (dataNode.has("tlyric")) {
            JsonNode tlyricNode = dataNode.get("tlyric");
            String tlyric = tlyricNode.asText();
            String[] split1 = text.split("\n");
            String[] split2 = tlyric.split("\n");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split1.length; i++) {
                sb.append(split1[i]);
                sb.append("\n");
                try {
                    sb.append(split2[i]);
                    sb.append("\n");
                } catch (Exception ignored) {}
            }
            outPutString = sb.toString();
        } else {
            outPutString = text;
        }
        System.out.println(outPutString);
        Files.write(file.toPath(), outPutString.getBytes(StandardCharsets.UTF_8));
    }

}
