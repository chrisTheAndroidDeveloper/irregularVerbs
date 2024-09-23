package com.example.irregularverbs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class TestActivity1 extends AppCompatActivity {

    private int num_mcq;
    private int num_frq;
    private int answer_mcq;
    private String answer_frq;
    private int score;
    private Intent intent;
    private int currentQ;
    private int correctMcq;
    private String correctFrq;

    public static Verb[] verbs = new Verb[]{
            new Verb("arise", "arose", "arisen", new String[]{"arisen", "arised", "arose", "arase"}, new String[]{"arosen", "arised", "arose", "aruse"}),
            new Verb("awake", "awoke", "awoken", new String[]{"awoken", "awake", "awaked", "awoke/awaked"}, new String[]{"awoke", "awaken", "awaked", "awoked/awaken"}),
            new Verb("be", "was/were", "been", new String[]{"was", "were", "been", "beed"}, new String[]{"was/were", "beed", "bet", "be"}),
            new Verb("bear", "bore", "born/borne", new String[]{"born", "born/borne", "beart", "ber"}, new String[]{"borne", "bore", "beart", "born"}),
            new Verb("beat", "beat", "beaten", new String[]{"beated", "bet", "beaten", "beten"}, new String[]{"beated", "beat", "beten", "beet"}),
            new Verb("begin", "began", "begun", new String[]{"begun", "begin", "begint", "beginen"}, new String[]{"began", "begin", "beginen", "begone"}),
            new Verb("bend", "bent", "bent", new String[]{"bend", "benden", "bant", "bended"}, new String[]{"bend", "bented", "bunt", "bentted"}),
            new Verb("bet", "bet", "bet", new String[]{"beet", "betten", "bit", "bot"}, new String[]{"beet", "betten", "bit", "bot"}),
            new Verb("bite", "bit", "bitten", new String[]{"bitted", "bat", "bite", "bitten"}, new String[]{"bit", "bitted", "bat", "bite"}),
            new Verb("blow", "blew", "blown", new String[]{"blown", "blowt", "blow", "bluw"}, new String[]{"blew", "blowt", "blowed", "bluw"}),
            new Verb("bleed", "bled", "bled", new String[]{"blet", "bleet", "bleed", "blat"}, new String[]{"blet", "bleet", "bleed", "blat"}),
            new Verb("break", "broke", "broken", new String[]{"broken", "broked", "breaked", "brak"}, new String[]{"broke", "broked", "breaked", "break"}),
            new Verb("bring", "brought", "brought", new String[]{"bringen", "bringht", "broght", "bringt"}, new String[]{"bringen", "bringht", "broght", "bringt"}),
            new Verb("broadcast", "broadcast", "broadcast", new String[]{"broadcasten", "broadcost", "broadcasted", "broadcoast"}, new String[]{"broadcasten", "broadcost", "broadcasted", "broadcoast"}),
            new Verb("build", "built", "built", new String[]{"build", "builded", "builden", "bild", "bilt"}, new String[]{"build", "builded", "builden", "bild", "bilt"}),
            new Verb("burn", "burnt/burned", "burnt/burned", new String[]{"burnt", "burned", "burn", "born"}, new String[]{"burnt", "burned", "burn", "born"}),
            new Verb("burst", "burst", "burst", new String[]{"bursten", "birst", "bursted", "berst"}, new String[]{"bursten", "birst", "bursted", "berst"}),
            new Verb("buy", "bought", "bought", new String[]{"bout", "buyed", "buyen", "baught"}, new String[]{"bout", "buyed/bought", "buyen", "baught"}),
            new Verb("can", "could", "(been able)", new String[]{"cauld", "cant", "canned", "coult"}, new String[]{"could", "cant", "canned", "coult"}),
            new Verb("catch", "caught", "caught", new String[]{"cought", "caut", "catched", "catch"}, new String[]{"cought", "caut", "catched", "catch"}),
            new Verb("choose", "chose", "chosen", new String[]{"chosen", "chost", "choose", "chosed"}, new String[]{"chose", "chost", "choose", "chosed"}),
            new Verb("come", "came", "come", new String[]{"come", "comt", "comed", "comen"}, new String[]{"came", "comt", "comed", "comen"}),
            new Verb("cost", "cost", "cost", new String[]{"cust", "costen", "costed", "cast"}, new String[]{"cust", "costen", "costed", "costten"}),
            new Verb("creep", "crept", "crept", new String[]{"creep", "crupt", "crep", "creeped"}, new String[]{"creep", "creeped", "crepen", "creepen"}),
            new Verb("cut", "cut", "cut", new String[]{"cuted", "cot", "cutted", "cutten"}, new String[]{"cutten", "cot", "cuted", "cutted"}),
            new Verb("deal", "dealt", "dealt", new String[]{"deald", "deal", "dealen", "dealed"}, new String[]{"deald", "deal", "dealen", "dealed"}),
            new Verb("dig", "dug", "dug", new String[]{"dig", "dag", "diggen", "deg"}, new String[]{"dig", "dag", "diggen", "deg"}),
            new Verb("do", "did", "done", new String[]{"doed", "wend", "do", "done"}, new String[]{"did", "do", "donnen", "didden"}),
            new Verb("draw", "drew", "drawn", new String[]{"draw", "druw", "drawed", "draft"}, new String[]{"drew", "druw", "drawen", "draft"}),
            new Verb("drink", "drank", "drunk", new String[]{"drunk", "drink", "dronk", "drinked"}, new String[]{"drank", "drink", "dranken", "drinken"}),
            new Verb("drive", "drove", "driven", new String[]{"droved", "drift", "drived", "drive"}, new String[]{"droven", "drove", "drived", "drift"}),
            new Verb("eat", "ate", "eaten", new String[]{"eat", "eated", "eatten", "ated"}, new String[]{"eatten", "ate", "ated", "aten"}),
            new Verb("fall", "fell", "fallen", new String[]{"falt", "fald", "fellen", "fault"}, new String[]{"falt", "fald", "fellen", "fell"}),
            new Verb("feed", "fed", "fed", new String[]{"feed", "fet", "fedded", "feeded"}, new String[]{"feed", "fedden", "fet", "fetten"}),
            new Verb("feel", "felt", "felt", new String[]{"feelen", "fellen", "feelt", "feeled"}, new String[]{"feelen", "fellen", "feelt", "feeled"}),
            new Verb("fight", "fought", "fought", new String[]{"fighten", "foughten", "feight", "fight"}, new String[]{"fighten", "foughten", "feight", "fight"}),
            new Verb("find", "found", "found", new String[]{"founden", "finded", "find", "fint"}, new String[]{"finden", "founded", "find", "fint"}),
            new Verb("flee", "fled", "fled", new String[]{"fleet", "flewn", "fleed", "flet"}, new String[]{"flown", "flewn", "fleed", "flet"}),
            new Verb("fly", "flew", "flown", new String[]{"flown", "flowd", "flew", "flet"}, new String[]{"flew", "fled", "flowd", "flewn"}),
            new Verb("forbid", "forbade", "forbidden", new String[]{"forbid", "forbidded", "forbided", "forbidden"}, new String[]{"forbaden", "forbiden", "forbid", "forbade"}),
            new Verb("forget", "forgot", "forgotten", new String[]{"forget", "forgetten", "forgot", "forgotted"}, new String[]{"forget", "forgetten", "forgot", "forgotted"}),
            new Verb("forgive", "forgave", "forgiven", new String[]{"forgive", "forgift", "forgived", "forgiven"}, new String[]{"forgave", "forgift", "forgaven", "forgive"}),
            new Verb("freeze", "froze", "frozen", new String[]{"frezed", "freezed", "frost", "frosted"}, new String[]{"frezen", "freezen", "frost", "frosten"}),
            new Verb("get", "got", "gotten", new String[]{"get", "getten", "getted", "gotted"}, new String[]{"get", "getten", "got", "gotted"}),
            new Verb("give", "gave", "given", new String[]{"give", "gift", "gived", "given"}, new String[]{"gave", "gift", "gaven", "give"}),
            new Verb("go", "went", "gone", new String[]{"goed", "got", "wend", "go"}, new String[]{"gotten", "got", "went", "wenten"}),
            new Verb("grow", "grew", "grown", new String[]{"grown", "grow", "growd", "growt"}, new String[]{"grew", "grow", "growd", "growt"}),
            new Verb("hang", "hang", "hung", new String[]{"hang", "hangen", "heng", "hangged"}, new String[]{"hang", "hangen", "hung", "hangged"}),
            new Verb("have", "had", "had", new String[]{"haven", "have", "haved", "hed"}, new String[]{"haven", "have", "haved", "hed"}),
            new Verb("hear", "heard", "heard", new String[]{"heart", "herd", "hert", "heared"}, new String[]{"heart", "herd", "hert", "hearen"}),
            new Verb("hide", "hid", "hidden", new String[]{"hit", "hid", "hide", "hided"}, new String[]{"hiden", "hid", "hide", "hided"}),
            new Verb("hit", "hit", "hit", new String[]{"hitted", "hut", "het", "hid"}, new String[]{"hitten", "hut", "het", "hid"}),
            new Verb("hold", "held", "held", new String[]{"hold", "holded", "helt", "helded"}, new String[]{"holden", "helden", "helt", "helded"}),
            new Verb("hurt", "hurt", "hurt", new String[]{"hurd", "hurted", "hert", "hurtted"}, new String[]{"hurd", "hurted", "hert", "hurten"}),
            new Verb("keep", "kept", "kept", new String[]{"kep", "keept", "keedped", "kepd"}, new String[]{"keepen", "keept", "keedped", "kepd"}),
            new Verb("kneel", "knelt", "knelt", new String[]{"kneld", "knel", "kneled", "knead"}, new String[]{"kneld", "kneln", "knelen", "kneet"}),
            new Verb("know", "knew", "known", new String[]{"known", "knowed", "knowt", "know"}, new String[]{"knew", "knewn", "knowed", "knowt"}),
            new Verb("lay", "laid", "laid", new String[]{"lain", "lay", "lait", "lat"}, new String[]{"lain", "lay", "lait", "lat"}),
            new Verb("lead", "led", "led", new String[]{"let", "leat", "lead", "lid"}, new String[]{"let", "leat", "lead", "lid"}),
            new Verb("lean", "leant/leaned", "leant/leaned", new String[]{"leant", "leaned", "leand", "lent"}, new String[]{"leant", "leaned", "leand", "lent"}),
            new Verb("learn", "learnt/learned", "learnt/learned", new String[]{"learned", "learnt", "learnd", "lernt"}, new String[]{"learned", "learnt", "learnd", "lernt"}),
            new Verb("leave", "left", "left", new String[]{"leaven", "leven", "leaved", "leved"}, new String[]{"leaven", "leven", "leaved", "leved"}),
            new Verb("lend", "lent", "lent", new String[]{"lend", "lended", "lended", "leant"}, new String[]{"lend", "lended", "lended", "leant"}),
            new Verb("let", "let", "let", new String[]{"letted", "lat", "lit", "letten"}, new String[]{"letten", "letted", "lat", "lit"}),
            new Verb("lie (position)", "lay", "lain", new String[]{"lied", "lait", "laid", "lain"}, new String[]{"lay", "laid", "lait", "lown"}),
            new Verb("lie (insincerity)", "lied", "lied", new String[]{"lay", "lait", "laid", "lain"}, new String[]{"lay", "lain", "lait", "laid"}),
            new Verb("light", "lit", "lit", new String[]{"light", "liet", "lite", "lighted"}, new String[]{"light", "liet", "lite", "lighten"}),
            new Verb("lose", "lost", "lost", new String[]{"losed", "losen", "luse", "lose"}, new String[]{"losed", "losen", "losten", "lose"}),
            new Verb("make", "made", "made", new String[]{"make", "maked", "mad", "mat"}, new String[]{"make", "maden", "mad", "mat"}),
            new Verb("mean", "meant", "meant", new String[]{"meand", "mend", "meaned", "ment"}, new String[]{"meand", "menen", "meanen", "ment"}),
            new Verb("meet", "met", "met", new String[]{"meet", "med", "meeted", "meed"}, new String[]{"meeten", "med", "meed", "meeden"}),
            new Verb("pay", "paid", "paid", new String[]{"pait", "payd", "payt", "payed"}, new String[]{"pait", "payd", "payt", "payen"}),
            new Verb("put", "put", "put", new String[]{"pud", "pot", "pat", "putted"}, new String[]{"pud", "pot", "pat", "putten"}),
            new Verb("read", "read", "read", new String[]{"red", "rad", "rode", "rid"}, new String[]{"red", "rad", "rode", "ridden"}),
            new Verb("rise", "rose", "risen", new String[]{"risen", "riset", "rist", "rised/rose"}, new String[]{"rose", "rise", "risen/rised", "rase"}),
            new Verb("ring", "rang", "rung", new String[]{"rung", "ring", "rong", "ringed"}, new String[]{"rang", "ring", "rong", "ringen"}),
            new Verb("rise", "rose", "risen", new String[]{"rised/rose", "rase", "risen", "rosen"}, new String[]{"rise", "rose", "rosen", "rase"}),
            new Verb("run", "ran", "run", new String[]{"run", "ron", "runt", "rund"}, new String[]{"ran", "ron", "runt", "rund"}),
            new Verb("say", "said", "said", new String[]{"sait", "sayd", "sayed", "say"}, new String[]{"sait", "sayd", "sayen", "say"}),
            new Verb("see", "saw", "seen", new String[]{"set", "seed", "seet", "seen"}, new String[]{"set", "seed", "seet", "saw"}),
            new Verb("seek", "sought", "sought", new String[]{"seek", "seeked", "sout", "seekt"}, new String[]{"seek", "seeken", "sout", "seekt"}),
            new Verb("sell", "sold", "sold", new String[]{"solt", "selt", "selled", "sell"}, new String[]{"sellen", "sell", "selt", "solt"}),
            new Verb("send", "sent", "sent", new String[]{"send", "sended/sent", "sant", "sand"}, new String[]{"send", "sended", "sant", "sand"}),
            new Verb("set", "set", "set", new String[]{"sat", "sot", "sut", "setted"}, new String[]{"sat", "sot", "sut", "setten"}),
            new Verb("sew", "sewed", "sewed/sewn", new String[]{"sewn", "sewed/sewn", "sewt", "sewd"}, new String[]{"sewn", "sewed", "sewt", "sewd"}),
            new Verb("shake", "shook", "shaken", new String[]{"shuk", "shaken", "shake", "shak"}, new String[]{"shuk", "shaken", "shake", "shak"}),
            new Verb("shine", "shone", "shone", new String[]{"shinet", "shint", "shane", "shun"}, new String[]{"shoon", "shint", "shane", "shun"}),
            new Verb("shoot", "shot", "shot", new String[]{"shud", "shood", "shoot", "shut"}, new String[]{"shud", "shood", "shoot", "shut"}),
            new Verb("show", "showed", "shown", new String[]{"showt", "showd", "shown", "shaw"}, new String[]{"showt", "showd", "shown", "shaw"}),
            new Verb("shrink", "shrank", "shrunk", new String[]{"shrunk", "shrinkt", "shrink", "shrinked"}, new String[]{"shrank", "shrinkt", "shranked", "shrinked"}),
            new Verb("shut", "shut", "shut", new String[]{"shud", "shat", "shot", "shutted"}, new String[]{"shud", "shat", "shot", "shutted"}),
            new Verb("sing", "sang", "sung", new String[]{"sung", "sing", "singed", "song"}, new String[]{"sang", "sing", "singed", "song"}),
            new Verb("sink", "sank", "sunk", new String[]{"sonk", "sinkt", "sink", "sank"}, new String[]{"sonk", "sinkt", "sinken", "sank"}),
            new Verb("sleep", "slept", "slept", new String[]{"sleep", "sleeped", "sleept", "slepd"}, new String[]{"sleep", "sleepen", "sleept", "sleped"}),
            new Verb("slide", "slid", "slid", new String[]{"slad", "slode", "slade", "slud"}, new String[]{"slad", "slode", "slade", "slud"}),
            new Verb("smell", "smelt", "smelt", new String[]{"smeld", "smelled", "smelled/smelt", "smell"}, new String[]{"smeld", "smelled", "smelled/smelt", "smell"}),
            new Verb("sit", "sat", "sat", new String[]{"sot", "sut", "sit", "sitted"}, new String[]{"sitten", "sut", "sot", "sit"}),
            new Verb("speak", "spoke", "spoken", new String[]{"speak", "spek", "speked", "speaked"}, new String[]{"speak", "spoked", "speken", "speaken"}),
            new Verb("spend", "spent", "spent", new String[]{"spant", "spunt", "spend", "spended"}, new String[]{"spant", "spunt", "spend", "spenden"}),
            new Verb("spit", "spat", "spat", new String[]{"spit", "sput", "spot", "spitted"}, new String[]{"spitten", "spit", "spot", "sput"}),
            new Verb("split", "split", "split", new String[]{"splat", "splot", "split", "splitted"}, new String[]{"splat", "split", "splut", "splitten"}),
            new Verb("spread", "spread", "spread", new String[]{"spreat", "spret", "spreaded", "sprat"}, new String[]{"spreat", "spret", "spreaded", "sprat"}),
            new Verb("spring", "sprang", "sprung", new String[]{"sprung", "spring", "springed", "springt"}, new String[]{"sprang", "spring", "springed", "springt"}),
            new Verb("stand", "stood", "stood", new String[]{"stud", "stod", "standed", "stand"}, new String[]{"stud", "stod", "standed", "stand"}),
            new Verb("steal", "stole", "stolen", new String[]{"stel", "stele", "steld", "stold"}, new String[]{"stel", "stelen", "steld", "stold"}),
            new Verb("stick", "stuck", "stuck", new String[]{"stack", "stook", "stick", "sticked"}, new String[]{"stack", "stook", "stick", "sticken"}),
            new Verb("sting", "stung", "stung", new String[]{"stink", "stank", "sting", "stang"}, new String[]{"stink", "stank", "sting", "stang"}),
            new Verb("stink", "stank", "stunk", new String[]{"stank", "stonk", "stink", "stinked"}, new String[]{"stunk", "stonk", "stink", "stinken"}),
            new Verb("strike", "struck", "struck", new String[]{"stroke", "strack", "strike", "striked"}, new String[]{"stroke", "strak", "strike", "striken"}),
            new Verb("swear", "swore", "sworn", new String[]{"sworn", "sware", "sweare", "sweart"}, new String[]{"swern", "sweart", "swore", "sweare"}),
            new Verb("sweep", "swept", "swept", new String[]{"swepd", "swep", "swap", "swop"}, new String[]{"swepd", "swep", "swap", "swop"}),
            new Verb("swim", "swam", "swum", new String[]{"swum", "swom", "swim", "swimpt"}, new String[]{"swam", "swom", "swim", "swimpt"}),
            new Verb("swing", "swung", "swung", new String[]{"swang", "swong", "swingt", "swing"}, new String[]{"swang", "swong", "swingt", "swing"}),
            new Verb("take", "took", "taken", new String[]{"toke", "tuk", "taken", "taked"}, new String[]{"token", "took", "take", "toke"}),
            new Verb("teach", "taught", "taught", new String[]{"teached", "teach", "tought", "thought"}, new String[]{"teached", "teach", "taughten", "teachen"}),
            new Verb("tear", "tore", "torn", new String[]{"teart", "torn", "tear", "ter"}, new String[]{"tore", "tear", "teart", "toor"}),
            new Verb("tell", "told", "told", new String[]{"tolt", "teld", "telt", "telld"}, new String[]{"tolt", "teld", "telt", "telld"}),
            new Verb("think", "thought", "thought", new String[]{"thinght", "thinkt", "thoght", "thunk"}, new String[]{"thinght", "thinkt", "thoght", "thunk"}),
            new Verb("throw", "threw", "thrown", new String[]{"thraw", "thruw", "threw/throwed", "thrown"}, new String[]{"thrawn", "threwn", "threw/thrown", "threw"}),
            new Verb("understand", "understood", "understood", new String[]{"understoond", "understad", "understod", "understand"}, new String[]{"understoond", "understad", "understod", "understand"}),
            new Verb("wake", "woke", "woken", new String[]{"woken", "wake", "waked", "woke/waked"}, new String[]{"woke", "waken", "waked", "woked/waken"}),
            new Verb("wear", "wore", "worn", new String[]{"worn", "ware", "weart", "weare"}, new String[]{"wore", "ware", "weart", "wearen"}),
            new Verb("weep", "wept", "wept", new String[]{"weept", "wep", "wope", "wape"}, new String[]{"weept", "wep", "wope", "wape"}),
            new Verb("win", "won", "won", new String[]{"wan", "wun", "wint", "wint/won"}, new String[]{"wan", "wun", "wint", "wint/won"}),
            new Verb("write", "wrote", "written", new String[]{"wrate", "wrute", "writ", "written"}, new String[]{"writen", "wrute", "writ", "wrote"})
    };

    private RadioButton[] rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        User user = MainActivity.getUser();

        if(user.day < Calendar.getInstance().get(Calendar.DAY_OF_YEAR) || user.year < Calendar.getInstance().get(Calendar.YEAR)){
            user.day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            user.year = Calendar.getInstance().get(Calendar.YEAR);
            if(user.dailyGoalsOn) {
                user.completedToday = 0;
                Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_WEEK);
                boolean[] days = user.daysOfPractice;
                int[] daysOfWeek = new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY,
                        Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
                for (int d = 0; d < days.length; d++) {
                    if (days[d] && daysOfWeek[d] == day) {
                        user.totalGoals++;
                    }
                }
            }
        }

        rbs = new RadioButton[]{findViewById(R.id.a1), findViewById(R.id.a2),
                findViewById(R.id.a3), findViewById(R.id.a4), findViewById(R.id.a5)};

        answer_mcq = -1;
        answer_frq = null;
        score = 0;
        currentQ = 1;
        
        if(user.askAboutRangeEachTime){

            ((EditText) findViewById(R.id.fromInput)).setText(user.testRangeStart+"");
            ((EditText) findViewById(R.id.toInput)).setText(user.testRangeEnd+"");

            ((SwitchCompat) findViewById(R.id.dontAskBtn)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    user.askAboutRangeEachTime = !user.askAboutRangeEachTime;
                }
            });

            ((Button) findViewById(R.id.testSetupSubmitBtn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.testRangeEnd = Integer.parseInt(((EditText) findViewById(R.id.toInput)).getText().toString());
                    user.testRangeStart = Integer.parseInt(((EditText) findViewById(R.id.fromInput)).getText().toString());
                    if(user.testRangeEnd - user.testRangeStart + 1 < user.numQuestions) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_to_few_verbs), Toast.LENGTH_LONG).show();
                    }else if(user.testRangeEnd > TestActivity1.verbs.length){
                        Toast.makeText(getApplicationContext(), "The upper boundary must be at most "+TestActivity1.verbs.length, Toast.LENGTH_LONG).show();
                    }else if(user.testRangeStart < 1){
                        Toast.makeText(getApplicationContext(), "The lower boundary must be at least 1", Toast.LENGTH_LONG).show();
                    }else{
                        findViewById(R.id.setupBlock).setVisibility(View.GONE);
                        findViewById(R.id.questionBlock).setVisibility(View.VISIBLE);
                    }
                }
            });
        }else{
            findViewById(R.id.setupBlock).setVisibility(View.GONE);
            findViewById(R.id.questionBlock).setVisibility(View.VISIBLE);
        }
        ArrayList<Verb> available = new ArrayList<>(Arrays.asList(verbs).subList(user.testRangeStart - 1, user.testRangeEnd));
        switch (user.questionType){
            case 1:
                num_mcq = 0;
                num_frq = user.numQuestions;
                findViewById(R.id.answer_frq_block).setVisibility(View.VISIBLE);
                findViewById(R.id.radioGroup2).setVisibility(View.GONE);
                break;
            case 2:
                num_mcq = user.numQuestions/2;
                num_frq = user.numQuestions - num_mcq;
                break;
            default:
                num_mcq = user.numQuestions;
                num_frq = 0;
                break;
        }
        newQuestion(user, available);
        intent = new Intent(TestActivity1.this, ResultsActivity.class);

        for(int i = 0; i < 5; i++){
            int num = i;
            rbs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        answer_mcq = num;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            if (getResources().getConfiguration().isNightModeActive()) {
                                buttonView.setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_variant_night, getTheme()));
                            }else {
                                buttonView.setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_variant, getTheme()));
                            }
                        }else {
                            buttonView.setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_variant, getTheme()));
                        }
                        for(int j = 0; j < 5; j++){
                            if(num != j){
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    if (getResources().getConfiguration().isNightModeActive()) {
                                        rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_night, getTheme()));
                                    }else {
                                        rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary, getTheme()));
                                    }
                                }else {
                                    rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary, getTheme()));
                                }
                            }
                        }
                    }
                }
            });
        }

        ((Button) findViewById(R.id.qSubmitBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer_mcq != -1){
                    if(correctMcq == answer_mcq){
                        score++;
                    }
                    intent.putExtra(""+currentQ, new String[]{rbs[answer_mcq].getText().toString(),
                            rbs[correctMcq].getText().toString(),
                            ((TextView) findViewById(R.id.task)).getText().toString(),
                            ((TextView) findViewById(R.id.verb)).getText().toString()});
                    if(num_mcq == 0){
                        answer_mcq = -1;
                    }
                }else{
                    answer_frq = ((EditText) findViewById(R.id.answer_frq)).getText().toString();
                    if(answer_frq.equals(correctFrq)){
                        score++;
                    }
                    intent.putExtra(""+currentQ, new String[]{correctFrq, answer_frq,
                            ((TextView) findViewById(R.id.task)).getText().toString(),
                            ((TextView) findViewById(R.id.verb)).getText().toString()});
                }
                currentQ++;
                newQuestion(user, available);
            }
        });

        findViewById(R.id.infobtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.view2).setVisibility(View.VISIBLE);
                findViewById(R.id.button).setVisibility(View.VISIBLE);
                findViewById(R.id.instructions).setVisibility(View.VISIBLE);
                findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findViewById(R.id.view2).setVisibility(View.GONE);
                        findViewById(R.id.button).setVisibility(View.GONE);
                        findViewById(R.id.instructions).setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void newQuestion(User user, ArrayList<Verb> available) {
        if (num_mcq > 0 || num_frq > 0) {
            int index = (int) (Math.random() * available.size());
            int what_form = (int) (Math.random() * 6);
            int yetAnotherSubtype = 0;
            if (num_mcq > 0) {
                for(int j = 0; j < 5; j++){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (getResources().getConfiguration().isNightModeActive()) {
                            rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary_night, getTheme()));
                        }else {
                            rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary, getTheme()));
                        }
                    }else {
                        rbs[j].setBackgroundTintList(getResources().getColorStateList(R.color.color_primary, getTheme()));
                    }
                }
                ((RadioGroup) findViewById(R.id.radioGroup2)).clearCheck();
                int correctIndex = (int) (Math.random() * 5);
                correctMcq = correctIndex;
                switch (what_form) {
                    case 0:
                    case 1:
                    case 2:
                        ((TextView) findViewById(R.id.task)).setText(getString(R.string.question_type1));
                        ((TextView) findViewById(R.id.verb)).setText(available.get(index).getFirst_form());
                        rbs[correctIndex].setText(available.get(index).getSecond_form());
                        int j = 0;
                        for (int i = 0; i < 5; i++) {
                            if (i != correctIndex) {
                                rbs[i].setText(available.get(index).getIncorrectOptions2()[j]);
                                j++;
                            }
                        }
                        break;
                    default:
                        ((TextView) findViewById(R.id.task)).setText(getString(R.string.question_type2));
                        ((TextView) findViewById(R.id.verb)).setText(available.get(index).getFirst_form());
                        rbs[correctIndex].setText(available.get(index).getThird_form());
                        int k = 0;
                        for (int i = 0; i < 5; i++) {
                            if (i != correctIndex) {
                                rbs[i].setText(available.get(index).getIncorrectOptions3()[k]);
                                k++;
                            }
                        }
                        break;
                }
                num_mcq--;
            } else {
                switch (what_form) {
                    case 0:
                    case 1:
                        ((TextView) findViewById(R.id.task)).setText(getString(R.string.question_type1));
                        ((TextView) findViewById(R.id.verb)).setText(available.get(index).getFirst_form());
                        correctFrq = available.get(index).getSecond_form();
                        break;
                    case 2:
                    case 3:
                        ((TextView) findViewById(R.id.task)).setText(getString(R.string.question_type2));
                        ((TextView) findViewById(R.id.verb)).setText(available.get(index).getFirst_form());
                        correctFrq = available.get(index).getThird_form();
                        break;
                    default:
                        ((TextView) findViewById(R.id.task)).setText(getString(R.string.question_type3));
                        yetAnotherSubtype = (int) (Math.random() * 2);
                        if (yetAnotherSubtype == 0) {
                            ((TextView) findViewById(R.id.verb)).setText(available.get(index).getSecond_form());
                        } else {
                            ((TextView) findViewById(R.id.verb)).setText(available.get(index).getThird_form());
                        }
                        if(available.get(index).getFirst_form().startsWith("lie")){
                            correctFrq = "lie";
                        }
                        correctFrq = available.get(index).getFirst_form();
                        break;
                }
                ((EditText) findViewById(R.id.answer_frq)).setText("");
                findViewById(R.id.radioGroup2).setVisibility(View.GONE);
                findViewById(R.id.answer_frq_block).setVisibility(View.VISIBLE);
                num_frq--;
            }
            available.remove(index);
        }else{
            int standard_score = (int) ((double) score / user.numQuestions * 100);
            user.testsCompleted++;
            user.completedToday++;
            user.totalScore += standard_score;
            if(user.completedToday == user.goal){
                user.completedGoals++;
            }
            if(standard_score > user.bestScore){
                user.bestScore = standard_score;
            }
            try {
                Gson gson = new Gson();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("user.json", Context.MODE_PRIVATE));
                outputStreamWriter.write(gson.toJson(user));
                outputStreamWriter.close();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to save your data", Toast.LENGTH_SHORT).show();
            }
            intent.putExtra("score", standard_score);
            startActivity(intent);
            finish();
        }
    }
}