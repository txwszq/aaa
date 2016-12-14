var _keys = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
function encrypt(l){
            l = "" + l;
            if (l == "") {
                return ""
            }
            var j = "";
            var s, q, o = "";
            var r, p, n, m = "";
            var k = 0;
            do {
                s = l.charCodeAt(k++);
                q = l.charCodeAt(k++);
                o = l.charCodeAt(k++);
                r = s >> 2;
                p = ((s & 3) << 4) | (q >> 4);
                n = ((q & 15) << 2) | (o >> 6);
                m = o & 63;
                if (isNaN(q)) {
                    n = m = 64
                } else {
                    if (isNaN(o)) {
                        m = 64
                    }
                }
                j = j + _keys.charAt(r) + _keys.charAt(p) + _keys.charAt(n) + _keys.charAt(m);
                s = q = o = "";
                r = p = n = m = ""
            } while (k < l.length);
            return j;
}