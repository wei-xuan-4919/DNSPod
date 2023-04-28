package cn.dnspod.utils;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weixuan
 * @date 2023/1/5 9:14
 * @Description:
 */
public class HeaderBuilder {
    public static class Builder {

        private final Map<String, String> header = new HashMap<>();

        public Builder add(String name, String value) {
            this.header.put(name, value);
            return this;
        }

        public Builder addAll(Header[] headers) {
            for (Header h : headers) {
                this.header.put(h.getName(), h.getValue());
            }
            return this;
        }

        public Header[] build() {
            List<Header> list = new ArrayList<>();
            for (String key : this.header.keySet()) {
                list.add(new BasicHeader(key, this.header.get(key)));
            }
            return list.toArray(new Header[0]);
        }
    }
}
