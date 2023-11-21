package videodownloader.privatebrowser.free.hd.statussaver.tool;

import android.net.Uri;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Pattern;

public class AdBlocker {
    private static final int ANCHORED = 3;
    private static final int COMMENT = 1;
    private static final int ELEM_HIDING = 2;
    private static final int EXCEPTION = 4;
    private static final int OTHER = 0;
    private static final int REGEX_NOOPT = 6;
    private static final int SIMPLE = 5;
    private static final Pattern spaceRegex = Pattern.compile("\\s+");
    private Pattern pattern;
    private final HashMap<String, Rule> rulesByDomain = new HashMap<>();
    private int count = 0;
    private final StringBuilder sb = new StringBuilder();
    private final int[] counters = new int[50];

        public static class Rule {
        private boolean matchAllPaths;
        private ArrayList<String> paths;
        private Pattern regex;
        private ArrayList<RuleWithDomain> rulesWithDomain;

        private Rule() {
        }
    }

        public static class RuleWithDomain {
        private HashSet<String> domains;
        private boolean inverseDomains;
        private boolean matchAllPaths;
        private String path;
        private Pattern regex;

        private RuleWithDomain() {
        }
    }

    public AdBlocker(File rootDir) {
        try {
            File[] listFiles = rootDir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    loadFromFile(file);
                }
            }
            StringBuilder sb = this.sb;
            if (sb == null || sb.length() <= 0) {
                return;
            }
            this.pattern = Pattern.compile(this.sb.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSimpleDomain(String domain) {
        try {
            Rule rule = this.rulesByDomain.get(domain);
            if (rule == null) {
                rule = new Rule();
                this.rulesByDomain.put(domain, rule);
            }
            if (rule.paths == null) {
                rule.paths = new ArrayList<>();
            }
            rule.paths.add("/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

                                private void loadFromAdblockFile(String firstline, BufferedReader br) {
        HashSet hashSet;
        boolean z;
        int indexOf;
        int indexOf2;
        String substring;
        while (true) {
            try {
                String readLine = br.readLine();
                if (readLine != null) {
                    boolean z2 = true;
                    this.count++;
                    if (!readLine.isEmpty()) {
                        if (readLine.startsWith("!")) {
                            int[] iArr = this.counters;
                            iArr[1] = iArr[1] + 1;
                        } else {
                            if (!readLine.contains("##") && !readLine.contains("#@#") && !readLine.contains("#?#")) {
                                if (readLine.startsWith("@@")) {
                                    int[] iArr2 = this.counters;
                                    iArr2[4] = iArr2[4] + 1;
                                } else if (readLine.startsWith("||")) {
                                    int[] iArr3 = this.counters;
                                    iArr3[3] = iArr3[3] + 1;
                                    int indexOf3 = readLine.indexOf(36);
                                    String substring2 = null;
                                    if (indexOf3 != -1) {
                                        int i = indexOf3 + 1;
                                        int indexOf4 = readLine.indexOf("domain=", i);
                                        if (indexOf4 != -1) {
                                            String[] split = readLine.substring(indexOf4 + 7).split("\\|");
                                            z = split.length > 0 && split[0].startsWith("~");
                                            hashSet = new HashSet(split.length);
                                            for (String str : split) {
                                                if (str.startsWith("~")) {
                                                    str = str.substring(1);
                                                }
                                                hashSet.add(str);
                                            }
                                            substring2 = indexOf3 != -1 ? readLine.substring(2) : readLine.substring(2, indexOf3);
                                            indexOf = substring2.indexOf(94);
                                            if (indexOf == -1) {
                                                indexOf = substring2.length();
                                            }
                                            indexOf2 = substring2.indexOf(47);
                                            if (indexOf2 == -1) {
                                                indexOf2 = substring2.length();
                                            }
                                            substring = substring2.substring(0, Math.min(indexOf, indexOf2));
                                            if (substring.indexOf(42) == -1) {
                                                Rule rule = this.rulesByDomain.get(substring);
                                                if (rule == null) {
                                                    rule = new Rule();
                                                    this.rulesByDomain.put(substring, rule);
                                                }
                                                String substring3 = substring2.substring(Math.min(indexOf, indexOf2));
                                                if (hashSet == null) {
                                                    if (rule.paths == null) {
                                                        rule.paths = new ArrayList<>();
                                                    }
                                                    rule.paths.add(substring3);
                                                } else {
                                                    RuleWithDomain ruleWithDomain = new RuleWithDomain();
                                                    ruleWithDomain.path = substring3;
                                                    ruleWithDomain.domains = hashSet;
                                                    ruleWithDomain.inverseDomains = z;
                                                    if (rule.rulesWithDomain == null) {
                                                        rule.rulesWithDomain = new ArrayList<>();
                                                    }
                                                    rule.rulesWithDomain.add(ruleWithDomain);
                                                }
                                            }
                                        }
                                    }
                                    indexOf = Objects.requireNonNull(substring2).indexOf(94);
                                    indexOf2 = substring2.indexOf(47);
                                    substring = substring2.substring(0, Math.min(indexOf, indexOf2));
                                    substring.indexOf(42);
                                } else {
                                    int[] iArr4 = this.counters;
                                    iArr4[0] = iArr4[0] + 1;
                                    if (readLine.indexOf(36) == -1) {
                                        int[] iArr5 = this.counters;
                                        iArr5[6] = iArr5[6] + 1;
                                        if (readLine.startsWith("|")) {
                                            readLine = readLine.substring(1);
                                        } else {
                                            z2 = false;
                                        }
                                        String ruleToRegex = ruleToRegex(readLine);
                                        if (z2) {
                                            ruleToRegex = "^" + ruleToRegex;
                                        }
                                        this.sb.append('|');
                                        this.sb.append(ruleToRegex);
                                    }
                                }
                            }
                            int[] iArr6 = this.counters;
                            iArr6[2] = iArr6[2] + 1;
                        }
                    }
                } else {
                    br.close();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void loadFromFile(File f) {
        String readLine;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null || !readLine.isEmpty()) {
                    break;
                }
            }
            if (readLine == null) {
                return;
            }
            if (readLine.startsWith("[")) {
                loadFromAdblockFile(readLine, bufferedReader);
            } else {
                loadFromHostsFile(readLine, bufferedReader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFromHostsFile(String firstline, BufferedReader br) {
        while (firstline != null) {
            try {
                if (!firstline.isEmpty() && !firstline.startsWith("#")) {
                    String[] split = spaceRegex.split(firstline);
                    if (split.length == 1) {
                        addSimpleDomain(split[0]);
                    } else {
                        for (int i = 1; i < split.length; i++) {
                            addSimpleDomain(split[i]);
                        }
                    }
                    this.count++;
                    int[] iArr = this.counters;
                    iArr[5] = iArr[5] + 1;
                }
                firstline = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

        public static String ruleToRegex(String rule) {
        boolean z;
        int length = rule.length();
        if (rule.endsWith("|")) {
            length--;
            z = true;
        } else {
            z = false;
        }
        StringBuilder sb = new StringBuilder(length + 32);
        for (int i = 0; i < length; i++) {
            char charAt = rule.charAt(i);
            if (charAt != '$' && charAt != '.' && charAt != '?') {
                switch (charAt) {
                    case '(':
                    case ')':
                    case '+':
                        break;
                    case '*':
                        sb.append(".*?");
                        continue;
                    default:
                        switch (charAt) {
                            case '[':
                            case '\\':
                            case ']':
                                break;
                            case '^':
                                sb.append("(?:[^\\w\\d_\\-.%]|$)");
                                break;
                            default:
                                switch (charAt) {
                                    case '{':
                                    case '|':
                                    case '}':
                                        break;
                                    default:
                                        sb.append(charAt);
                                        continue;
                                }
                        }
                        break;
                }
            }
            sb.append('\\');
            sb.append(charAt);
        }
        if (z) {
            sb.append('$');
        }
        return sb.toString();
    }

    public String getPath(Uri url) {
        String encodedQuery = url.getEncodedQuery();
        if (encodedQuery == null) {
            return url.getEncodedPath();
        }
        return url.getEncodedPath() + "?" + encodedQuery;
    }

            public boolean shouldBlock(Uri url, String mainPage) {
        boolean z = false;
        if (url == null) {
            return false;
        }
        try {
            if (!"http".equals(url.getScheme()) && !"https".equals(url.getScheme())) {
                return false;
            }
            boolean shouldBlockHash = shouldBlockHash(url, mainPage);
            if (!shouldBlockHash) {
                Pattern pattern = this.pattern;
                return z | shouldBlockHash;
            }
            z = true;
            return z | shouldBlockHash;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean shouldBlockHash(Uri url, String mainPage) {
        if (url == null) {
            return false;
        }
        String host = url.getHost();
        while (true) {
            int indexOf = Objects.requireNonNull(host).indexOf(46);
            if (indexOf == -1) {
                return false;
            }
            Rule rule = this.rulesByDomain.get(host);
            if (rule != null && shouldBlockHashRule(rule, url, mainPage)) {
                return true;
            }
            host = host.substring(indexOf + 1);
        }
    }

    public boolean shouldBlockHashRegular(Rule r, Uri url, String mainPage) {
        if (url == null) {
            return false;
        }
        if (r.matchAllPaths) {
            return true;
        }
        if (r.regex == null && r.paths == null) {
            return false;
        }
        if (r.regex == null) {
            if (r.paths.size() == 1) {
                String str = (String) r.paths.get(0);
                if (!str.equals("/") && !str.equals("^")) {
                    StringBuilder m = new StringBuilder("^");
                    m.append(ruleToRegex(str));
                    r.regex = Pattern.compile(m.toString());
                } else {
                    r.paths = null;
                    r.matchAllPaths = true;
                    return true;
                }
            } else {
                StringBuilder sb = new StringBuilder("^(?:");
                sb.append(ruleToRegex((String) r.paths.get(0)));
                for (int i = 1; i < r.paths.size(); i++) {
                    sb.append('|');
                    sb.append(ruleToRegex((String) r.paths.get(i)));
                }
                sb.append(')');
                r.regex = Pattern.compile(sb.toString());
            }
            r.paths = null;
        }
        return r.regex.matcher(getPath(url)).find();
    }

    public boolean shouldBlockHashRule(Rule r, Uri url, String mainPage) {
        return shouldBlockHashRegular(r, url, mainPage) || shouldBlockHashWithDomain(r, url, mainPage);
    }

    public boolean shouldBlockHashWithDomain(Rule r, Uri url, String mainPage) {
        String host;
        if (url != null && r.rulesWithDomain != null && (host = Uri.parse(mainPage).getHost()) != null && !mainPage.isEmpty()) {
            Iterator it = r.rulesWithDomain.iterator();
            String str = null;
            loop0:
            while (it.hasNext()) {
                RuleWithDomain ruleWithDomain = (RuleWithDomain) it.next();
                while (true) {
                    int indexOf = host.indexOf(46);
                    if (indexOf != -1) {
                        if (ruleWithDomain.domains.contains(host) != ruleWithDomain.inverseDomains) {
                            if (ruleWithDomain.matchAllPaths) {
                                return ruleWithDomain.inverseDomains;
                            }
                            if (ruleWithDomain.regex == null) {
                                if (ruleWithDomain.path.equals("/") || ruleWithDomain.path.equals("^")) {
                                    break loop0;
                                }
                                StringBuilder m = new StringBuilder("^");
                                m.append(ruleToRegex(ruleWithDomain.path));
                                ruleWithDomain.regex = Pattern.compile(m.toString());
                            }
                            if (str == null) {
                                str = getPath(url);
                            }
                            if (ruleWithDomain.regex.matcher(str).find()) {
                                return true;
                            }
                        }
                    }
                    break;
                }
                ruleWithDomain.path = null;
                ruleWithDomain.matchAllPaths = true;
                return true;
            }
        }
        return false;
    }
}
