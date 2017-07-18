/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package D_common;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class J_s {
    
    public static final String UNPACK_STCRIPT = 
"var JavascriptObfuscator = {\n" +
"    detect: function(str) {\n" +
"        return /^var _0x[a-f0-9]+ ?\\= ?\\[/.test(str);\n" +
"    },\n" +
"\n" +
"    unpack: function(str) {\n" +
"        if (JavascriptObfuscator.detect(str)) {\n" +
"            var matches = /var (_0x[a-f\\d]+) ?\\= ?\\[(.*?)\\];/.exec(str);\n" +
"            if (matches) {\n" +
"                var var_name = matches[1];\n" +
"                var strings = JavascriptObfuscator._smart_split(matches[2]);\n" +
"                str = str.substring(matches[0].length);\n" +
"                for (var k in strings) {\n" +
"                    str = str.replace(new RegExp(var_name + '\\\\[' + k + '\\\\]', 'g'),\n" +
"                        JavascriptObfuscator._fix_quotes(JavascriptObfuscator._unescape(strings[k])));\n" +
"                }\n" +
"            }\n" +
"        }\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"    _fix_quotes: function(str) {\n" +
"        var matches = /^\"(.*)\"$/.exec(str);\n" +
"        if (matches) {\n" +
"            str = matches[1];\n" +
"            str = \"'\" + str.replace(/'/g, \"\\\\'\") + \"'\";\n" +
"        }\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"    _smart_split: function(str) {\n" +
"        var strings = [];\n" +
"        var pos = 0;\n" +
"        while (pos < str.length) {\n" +
"            if (str.charAt(pos) === '\"') {\n" +
"                // new word\n" +
"                var word = '';\n" +
"                pos += 1;\n" +
"                while (pos < str.length) {\n" +
"                    if (str.charAt(pos) === '\"') {\n" +
"                        break;\n" +
"                    }\n" +
"                    if (str.charAt(pos) === '\\\\') {\n" +
"                        word += '\\\\';\n" +
"                        pos++;\n" +
"                    }\n" +
"                    word += str.charAt(pos);\n" +
"                    pos++;\n" +
"                }\n" +
"                strings.push('\"' + word + '\"');\n" +
"            }\n" +
"            pos += 1;\n" +
"        }\n" +
"        return strings;\n" +
"    },\n" +
"\n" +
"\n" +
"    _unescape: function(str) {\n" +
"        // inefficient if used repeatedly or on small strings, but wonderful on single large chunk of text\n" +
"        for (var i = 32; i < 128; i++) {\n" +
"            str = str.replace(new RegExp('\\\\\\\\x' + i.toString(16), 'ig'), String.fromCharCode(i));\n" +
"        }\n" +
"        str = str.replace(/\\\\x09/g, \"\\t\");\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"    run_tests: function(sanity_test) {\n" +
"        var t = sanity_test || new SanityTest();\n" +
"\n" +
"        t.test_function(JavascriptObfuscator._smart_split, \"JavascriptObfuscator._smart_split\");\n" +
"        t.expect('', []);\n" +
"        t.expect('\"a\", \"b\"', ['\"a\"', '\"b\"']);\n" +
"        t.expect('\"aaa\",\"bbbb\"', ['\"aaa\"', '\"bbbb\"']);\n" +
"        t.expect('\"a\", \"b\\\\\\\"\"', ['\"a\"', '\"b\\\\\\\"\"']);\n" +
"        t.test_function(JavascriptObfuscator._unescape, 'JavascriptObfuscator._unescape');\n" +
"        t.expect('\\\\x40', '@');\n" +
"        t.expect('\\\\x10', '\\\\x10');\n" +
"        t.expect('\\\\x1', '\\\\x1');\n" +
"        t.expect(\"\\\\x61\\\\x62\\\\x22\\\\x63\\\\x64\", 'ab\"cd');\n" +
"        t.test_function(JavascriptObfuscator.detect, 'JavascriptObfuscator.detect');\n" +
"        t.expect('', false);\n" +
"        t.expect('abcd', false);\n" +
"        t.expect('var _0xaaaa', false);\n" +
"        t.expect('var _0xaaaa = [\"a\", \"b\"]', true);\n" +
"        t.expect('var _0xaaaa=[\"a\", \"b\"]', true);\n" +
"        t.expect('var _0x1234=[\"a\",\"b\"]', true);\n" +
"        return t;\n" +
"    }\n" +
"\n" +
"\n" +
"};\n" +
"\n" +
"var Urlencoded = {\n" +
"    detect: function(str) {\n" +
"        // the fact that script doesn't contain any space, but has %20 instead\n" +
"        // should be sufficient check for now.\n" +
"        if (str.indexOf(' ') === -1) {\n" +
"            if (str.indexOf('%2') !== -1) return true;\n" +
"            if (str.replace(/[^%]+/g, '').length > 3) return true;\n" +
"        }\n" +
"        return false;\n" +
"    },\n" +
"\n" +
"    unpack: function(str) {\n" +
"        if (Urlencoded.detect(str)) {\n" +
"            if (str.indexOf('%2B') !== -1 || str.indexOf('%2b') !== -1) {\n" +
"                // \"+\" escaped as \"%2B\"\n" +
"                return unescape(str.replace(/\\+/g, '%20'));\n" +
"            } else {\n" +
"                return unescape(str);\n" +
"            }\n" +
"        }\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"\n" +
"\n" +
"    run_tests: function(sanity_test) {\n" +
"        var t = sanity_test || new SanityTest();\n" +
"        t.test_function(Urlencoded.detect, \"Urlencoded.detect\");\n" +
"        t.expect('', false);\n" +
"        t.expect('var a = b', false);\n" +
"        t.expect('var%20a+=+b', true);\n" +
"        t.expect('var%20a=b', true);\n" +
"        t.expect('var%20%21%22', true);\n" +
"        t.expect('javascript:(function(){var%20whatever={init:function(){alert(%22a%22+%22b%22)}};whatever.init()})();', true);\n" +
"        t.test_function(Urlencoded.unpack, 'Urlencoded.unpack');\n" +
"\n" +
"        t.expect('javascript:(function(){var%20whatever={init:function(){alert(%22a%22+%22b%22)}};whatever.init()})();',\n" +
"            'javascript:(function(){var whatever={init:function(){alert(\"a\"+\"b\")}};whatever.init()})();'\n" +
"        );\n" +
"        t.expect('', '');\n" +
"        t.expect('abcd', 'abcd');\n" +
"        t.expect('var a = b', 'var a = b');\n" +
"        t.expect('var%20a=b', 'var a=b');\n" +
"        t.expect('var%20a=b+1', 'var a=b+1');\n" +
"        t.expect('var%20a=b%2b1', 'var a=b+1');\n" +
"        return t;\n" +
"    }\n" +
"\n" +
"\n" +
"};\n" +
"var P_A_C_K_E_R = {\n" +
"    detect: function(str) {\n" +
"        return (P_A_C_K_E_R.get_chunks(str).length > 0);\n" +
"    },\n" +
"\n" +
"    get_chunks: function(str) {\n" +
"        var chunks = str.match(/eval\\(\\(?function\\(.*?(,0,\\{\\}\\)\\)|split\\('\\|'\\)\\)\\))($|\\n)/g);\n" +
"        return chunks ? chunks : [];\n" +
"    },\n" +
"\n" +
"    unpack: function(str) {\n" +
"        var chunks = P_A_C_K_E_R.get_chunks(str),\n" +
"            chunk;\n" +
"        for (var i = 0; i < chunks.length; i++) {\n" +
"            chunk = chunks[i].replace(/\\n$/, '');\n" +
"            str = str.split(chunk).join(P_A_C_K_E_R.unpack_chunk(chunk));\n" +
"        }\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"    unpack_chunk: function(str) {\n" +
"        var unpacked_source = '';\n" +
"        var __eval = eval;\n" +
"        if (P_A_C_K_E_R.detect(str)) {\n" +
"            try {\n" +
"                eval = function(s) { // jshint ignore:line\n" +
"                    unpacked_source += s;\n" +
"                    return unpacked_source;\n" +
"                }; // jshint ignore:line\n" +
"                __eval(str);\n" +
"                if (typeof unpacked_source === 'string' && unpacked_source) {\n" +
"                    str = unpacked_source;\n" +
"                }\n" +
"            } catch (e) {\n" +
"                // well, it failed. we'll just return the original, instead of crashing on user.\n" +
"            }\n" +
"        }\n" +
"        eval = __eval; // jshint ignore:line\n" +
"        return str;\n" +
"    },\n" +
"\n" +
"    run_tests: function(sanity_test) {\n" +
"        var t = sanity_test || new SanityTest();\n" +
"\n" +
"        var pk1 = \"eval(function(p,a,c,k,e,r){e=String;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\\\\\b'+e(c)+'\\\\\\\\b','g'),k[c]);return p}('0 2=1',3,3,'var||a'.split('|'),0,{}))\";\n" +
"        var unpk1 = 'var a=1';\n" +
"        var pk2 = \"eval(function(p,a,c,k,e,r){e=String;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\\\\\b'+e(c)+'\\\\\\\\b','g'),k[c]);return p}('0 2=1',3,3,'foo||b'.split('|'),0,{}))\";\n" +
"        var unpk2 = 'foo b=1';\n" +
"        var pk_broken = \"eval(function(p,a,c,k,e,r){BORKBORK;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\\\\\b'+e(c)+'\\\\\\\\b','g'),k[c]);return p}('0 2=1',3,3,'var||a'.split('|'),0,{}))\";\n" +
"        var pk3 = \"eval(function(p,a,c,k,e,r){e=String;if(!''.replace(/^/,String)){while(c--)r[c]=k[c]||c;k=[function(e){return r[e]}];e=function(){return'\\\\\\\\w+'};c=1};while(c--)if(k[c])p=p.replace(new RegExp('\\\\\\\\b'+e(c)+'\\\\\\\\b','g'),k[c]);return p}('0 2=1{}))',3,3,'var||a'.split('|'),0,{}))\";\n" +
"        var unpk3 = 'var a=1{}))';\n" +
"\n" +
"        t.test_function(P_A_C_K_E_R.detect, \"P_A_C_K_E_R.detect\");\n" +
"        t.expect('', false);\n" +
"        t.expect('var a = b', false);\n" +
"        t.test_function(P_A_C_K_E_R.unpack, \"P_A_C_K_E_R.unpack\");\n" +
"        t.expect(pk_broken, pk_broken);\n" +
"        t.expect(pk1, unpk1);\n" +
"        t.expect(pk2, unpk2);\n" +
"        t.expect(pk3, unpk3);\n" +
"\n" +
"        var filler = '\\nfiller\\n';\n" +
"        t.expect(filler + pk1 + \"\\n\" + pk_broken + filler + pk2 + filler, filler + unpk1 + \"\\n\" + pk_broken + filler + unpk2 + filler);\n" +
"\n" +
"        return t;\n" +
"    }\n" +
"\n" +
"\n" +
"};\n" +
"\n" +
"function unpacker_filter(source) {\n" +
"            var trailing_comments = '',\n" +
"                comment = '',\n" +
"                unpacked = '',\n" +
"                found = false;\n" +
"\n" +
"            // cut trailing comments\n" +
"            do {\n" +
"                found = false;\n" +
"                if (/^\\s*\\/\\*/.test(source)) {\n" +
"                    found = true;\n" +
"                    comment = source.substr(0, source.indexOf('*/') + 2);\n" +
"                    source = source.substr(comment.length).replace(/^\\s+/, '');\n" +
"                    trailing_comments += comment + \"\\n\";\n" +
"                } else if (/^\\s*\\/\\//.test(source)) {\n" +
"                    found = true;\n" +
"                    comment = source.match(/^\\s*\\/\\/.*/)[0];\n" +
"                    source = source.substr(comment.length).replace(/^\\s+/, '');\n" +
"                    trailing_comments += comment + \"\\n\";\n" +
"                }\n" +
"            } while (found);\n" +
"\n" +
"            var unpackers = [P_A_C_K_E_R, Urlencoded, JavascriptObfuscator/*, MyObfuscate*/];\n" +
"            for (var i = 0; i < unpackers.length; i++) {\n" +
"                if (unpackers[i].detect(source)) {\n" +
"                    unpacked = unpackers[i].unpack(source);\n" +
"                    if (unpacked != source) {\n" +
"                        source = unpacker_filter(unpacked);\n" +
"                    }\n" +
"                }\n" +
"            }\n" +
"\n" +
"            return trailing_comments + source;\n" +
"        }";
    
    
    private static ScriptEngine jsEngine = null;
    public static String unpackJavaScript(String js) {
        try {
            if (jsEngine == null) {
                ScriptEngineManager engineManager = new ScriptEngineManager();
                jsEngine = engineManager.getEngineByName("nashorn");
                jsEngine.eval(UNPACK_STCRIPT);
            }
            Invocable invocable = (Invocable) jsEngine;
            String jsUnpack = invocable.invokeFunction("unpacker_filter", js).toString();
            return jsUnpack;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
     
}


