import httpRequest.HttpUtil;
import threadPool.ThreadPool;
import threadPool.ThreadPoolManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestPool {
    public static void main(String[] args) {
        ThreadPool tp = ThreadPoolManager.getThreadPool(7);
        List<Runnable> taskList = new ArrayList<Runnable>();
        for (int i = 0; i < 10000; i++) {
            taskList.add(new Task());
        }
        tp.execute(taskList);
        System.out.println(tp);
        tp.destroy();//所有线程都执行完成才destroy
        System.out.println(tp);
    }
}
class Task implements Runnable{
    private static volatile int i=1;
    @Override
    public void run() {
        System.out.println("当前处理的线程是："+Thread.currentThread().getName()+
                "。执行任务"+i++ +"完成");
        //Scanner console=new Scanner(System.in);
        //while (true){
        //System.out.println("Enter your param：");
        //String str=console.next();
        HttpUtil httpUtil=new HttpUtil();
        try {
            String string="{\n" +
                    "total: 24485719,\n" +
                    "QTime: 353,\n" +
                    "start: 1,\n" +
                    "page: 1,\n" +
                    "results: [\n" +
                    "{\n" +
                    "ID: \"bd5c28ff839fc804aff15d6a1e6623c4\",\n" +
                    "MD2: \"1728221eee1bed3b29b8281c86496fa6\",\n" +
                    "_route_: \"shard2\",\n" +
                    "_version_: 1583311665887182800,\n" +
                    "GroupName: \"newsphfhu\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"5b427bcb57b2ab5da846e5212be62349\",\n" +
                    "AddOn: \"1506464713000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"7463a83f01fe7a6b9246ed9a9993ca7 5b427bcb57b2ab5da846e5212be62349\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2937030\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"441756f88df49e5b305b7ff918fc63ca\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"newsphfhu_entertainment\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \" Anna Kendrick, REBEL Wilson and more are back in PITCH PERFECT 3, hitting theaters December 22, 2018. Check out a first look at the all-new trailer below! Anna Kendrick, REBEL Wilson, Hailee Steinfeld, Brittany Snow, Anna Camp, Hana Mae Lee, Ester Dean ..... 9 Published By - US 24online - 2017.09.26. 02:53 Share | \",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"0\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"phf.hu\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"67463a83f01fe7a6b9246ed9a9993ca7\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1545418380000\",\n" +
                    "Url: \"http://news.phf.hu/news/video-the-bellas-are-back-anna-kendrick-more-in-pitch-perfect-3-trailer\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2937254\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"VIDEO: The Bellas Are Back! Anna Kendrick & More in PITCH PERFECT 3 Trailer\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"7cb1a6b60a3a3a4389ff4e603d61231d\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"06aa5c3fa7183f1f770aa41621b42923\",\n" +
                    "MD2: \"e2e9e70af004938f1987e9022b9ca820\",\n" +
                    "_route_: \"shard6\",\n" +
                    "_version_: 1583311906565783600,\n" +
                    "GroupName: \"newsphfhu\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"81878340592e54f5f3c502a9d8616827\",\n" +
                    "AddOn: \"1507060108000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"8cc1b4e27c9e76b496b9effad160839 81878340592e54f5f3c502a9d8616827\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2937030\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"4a534d6b00c25437d41a8d65f562fdd8\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"newsphfhu_business\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \" Movie on Manmohan to have Italian actress for Sonia Gandhi, Hollywood actor for Rahul\u200B\u200BThe movie, with a cast of 128, is slated for a December 21, 2018 release, a few months before the early-middle 2019 general elections.... 0 Published By - The Economic Times Home - 2017.10.03. 21:13 Share | \",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"0\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"phf.hu\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"18cc1b4e27c9e76b496b9effad160839\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1545397980000\",\n" +
                    "Url: \"http://news.phf.hu/news/movie-on-manmohan-to-have-italian-actress-for-sonia-gandhi-hollywood-actor-for-rahul\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2937250\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Movie on Manmohan to have Italian actress for Sonia Gandhi, Hollywood actor for Rahul\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"efce61047a18a6f1e0d5bb68de1a42e5\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"5cdf93aea99084b4441ef9ca1b3ebdc9\",\n" +
                    "MD2: \"de2ab3dc10f691d098a1eb7f3af50ef0\",\n" +
                    "_route_: \"shard3\",\n" +
                    "_version_: 1583311461593120800,\n" +
                    "GroupName: \"newsphfhu\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"148d87fad47e908c34497906181d9307\",\n" +
                    "AddOn: \"1507078333000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"fecfabd5f64f1af16e2329e30184792 148d87fad47e908c34497906181d9307\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2937030\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"4ee1f24ef10ce5240dc9d44b2786d23c\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"newsphfhu_business\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \" Coming soon: A film on Manmohan with Italian actress for Sonia, Irish actor for Rahul\u200B\u200BThe movie, with a cast of 128, is slated for a December 21, 2018 release, a few months before the early-middle 2019 general elections.... 0 Published By - The Economic Times Home - 2017.10.04. 02:43 Share | \",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"0\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"phf.hu\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"ffecfabd5f64f1af16e2329e30184792\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1545331380000\",\n" +
                    "Url: \"http://news.phf.hu/news/coming-soon-a-film-on-manmohan-with-italian-actress-for-sonia-irish-actor-for-rahul\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2937250\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Coming soon: A film on Manmohan with Italian actress for Sonia, Irish actor for Rahul\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"e15fb9f4cd0e1c72f581a04ea5786c1d\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"d4f8c6094e5ec92be980808377a7942b\",\n" +
                    "MD2: \"cd6eec69dc096f2963646338630e7e99\",\n" +
                    "_route_: \"shard0\",\n" +
                    "_version_: 1583311505883922400,\n" +
                    "GroupName: \"Reuters\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"f7a2ec0b6d698045a8c8ebe97899485c\",\n" +
                    "AddOn: \"1507814550000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"86f06f648ee461f34c2ecb8e12669e4 f7a2ec0b6d698045a8c8ebe97899485c\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2687553\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"8b69e223510534a21ab64efac3c4f007\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"Reuters_home\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"ZURICH (Reuters) - Landmark Roche patents that helped launch the modern biotechnology era end next year, extinguishing a source of billions of dollars in income for the Swiss drugmaker and piling pressure on its new medicines to succeed. Roche’s so-called “Cabilly patents” protect a pivotal step in manufacturing therapeutic antibodies, but end on Dec. 18, 2018. For decades, these patents allowed Roche to extract cash from dozens of drugmakers. In 2018 alone, experts estimate the patents will reap $1 billion for Roche and California’s City of Hope medical center, where the technology was developed nearly 40 years ago. The patent expirations come just as Roche’s $20-billon-per-year drug trio Rituxan, Avastin and Herceptin are also losing protection, exposing them to cheaper biosimilar copies. Roche also has rising financial obligations to other companies whose patented know-how contributed to its newer medicines. “Beyond the sales losses through biosimilars, hundreds of millions in royalties will evaporate,” Zuercher Kantonalbank analyst Michael Nawrath said. “Roche faces a double whammy as it becomes a net payer of royalties.” For instance, U.S.-based Biogen is entitled to between 13.5 and 24 percent of U.S. sales and 3 percent of sales elsewhere from Roche’s new multiple sclerosis drug Ocrevus, whose annual revenue may hit $4 billion by 2022. The Cabilly manufacturing patents, among the drug industry’s most famous and lucrative, are named after scientist Shmuel Cabilly who led the City of Hope team behind the technology in the early 1980s. They have been pivotal to making hundreds of biotech drugs, including AbbVie’s $16 billion-a-year seller Humira, the world’s top-selling prescription medicine. As sales of such biological medicines boomed, Roche benefited from growing Cabilly licensing fees while also successfully fending off lawsuits from competitors unhappy with the arrangement. Cabilly brought in an estimated $800 million in 2016 - around $500 million for Roche, $300 million for City of Hope - up from $256 million in 2007. Royalty income now accounts for 3 to 4 percent of Roche’s $41 billion annual drug sales. “There will be a significant step change to royalty income,” a spokeswoman acknowledged. Chief Executive Severin Schwan insists Roche will continue to grow sales and profit thanks to new, potential blockbusters, including Ocrevus, yet-to-be-approved ACE910 for haemophilia and cancer immunotherapy Tecentriq. In July, Schwan upgraded 2017 guidance to mid-single-digit percentage growth in sales. Others are not so upbeat.PIPELINE PRESSURE “I‘m losing confidence that they will fight their way out of this,” Candriam fund manager Rudi Van den Eynde told Reuters. “I think the pipeline in the end will not be enough to fully compensate what is happening there.” Jeffrey Holford, a Jefferies analyst, forecasts Cabilly revenue losses, coupled with expiring patents on Roche’s aging franchise of older drugs, will keep the drugmaker from boosting operating margins until at least 2020. For City of Hope, whose Cabilly share has risen five-fold in a decade, the expiration has big consequences, too. Hospital officials did not respond to Reuters’ queries, but bondholder reports show it aims to cut research and clinical costs “in the face of the expected loss of most royalty revenue,” according to a Moody’s note. Meanwhile, drugmakers Mylan, Merck, Sanofi and GlaxoSmithKline that have sought repeatedly to weaken Cabilly at the U.S. patent office and courts won’t regret its demise. “Drug companies that fought unsuccessfully multiple legal battles against the longest-lived biotech patents – effectively 29 years — will no longer face the Cabilly bottleneck,” said Konstantin Linnik, a partner at Boston law firm Nutter McClennen & Fish. Additional reporting by Paul Arnold in Zurich; Editing by Ben Hirschler and Mark Potter\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"0\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"reuters.com\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"086f06f648ee461f34c2ecb8e12669e4\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1545062400000\",\n" +
                    "Url: \"http://in.reuters.com/article/roche-patent/double-whammy-for-roche-as-manufacturing-patent-losses-loom-idINKBN1CH1VC?il=0\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2687592\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"&#x27;Double whammy&#x27; for Roche as manufacturing patent losses loom\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"2afd363e268037e04765ac9be97d391d\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"3a75a2c7a7977e2be203050936c1a81b\",\n" +
                    "MD2: \"c2529c59361bf57f5029f7d70767f3b4\",\n" +
                    "_route_: \"shard6\",\n" +
                    "_version_: 1583313568690864000,\n" +
                    "GroupName: \"longroom\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"2ac2b64f9cfe91516a2216bfe9f90184\",\n" +
                    "AddOn: \"1506631124000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"e5f7cda329c36e5aa2caed5bb9e091d 2ac2b64f9cfe91516a2216bfe9f90184\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2931790\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"1\",\n" +
                    "ImageUrl: \"http://i.dailymail.co.uk/i/pix/2017/09/28/19/44D41D8C00000578-0-image-a-25_1506623994525.jpg\",\n" +
                    "ArticleHash: \"18ee614b57c198c62a08ef7dbfea9844\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"longroom_entertainment\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"Your browser does not support HTML5 video. Cher has announced a musical based on her life and six-decade career will come to Broadway in 2018. The singer and actress, 71, admitted she found it 'crazy, exciting and bizarre' to see her experiences come to life in a theatrical production. Cher - Show - Neil - Simon - TheaterThe Cher Show will open at the Neil Simon Theater in New York City in the fall of 2018. 'My life as a musical on Broadway. It seems crazy, exciting and bizarre - but that’s probably how my life seems to most people,' the Believe songstress said in a statement on Thursday. Musical - Cher - Hits - Life - SingerThe biographical musical will feature Cher’s chart-topping hits, and will follow the life of the singer, actress, and icon. The If I Could Turn Back Time belter teased back in July that a musical about her life and career would hit Broadway in 2018. Reading - Place - January - Cher - StampA staged reading of the musical took place in January, which appeared to get Cher’s stamp of approval. She tweeted: 'I SOBBED & LAUGHED,& I WAS PREPARED NOT 2LIKE IT. AUDIENCE CLAPPED AFTER SONGS,& GAVE IT STANDING OVATION.' Cher - Plans - BroadwayCher first mentioned plans for a Broadway musical based on...Mail Online29 other people are viewing this story50 Top Stories Breaking Now!Where The World Finds Its News - LongRoom News!Wake Up To Breaking News!\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"6\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"longroom.com\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"2e5f7cda329c36e5aa2caed5bb9e091d\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1544025600000\",\n" +
                    "Url: \"https://www.longroom.com/discussion/704340/my-life-as-a-musical-crazy-exciting-and-bizarre-cher-announces-a-show-based-on-her-six-decade-career-will-hit-broadway-in-2018\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2931805\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"exciting and bizarre': Cher announces a show based on her six\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"48290967942d253abecc813c7550ff80\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"caaeab0715f255dc3a732b3e2680b057\",\n" +
                    "MD2: \"2c50e43d5f58b60b4aaf10be5877708d\",\n" +
                    "_route_: \"shard2\",\n" +
                    "_version_: 1583311534239514600,\n" +
                    "GroupName: \"longroom\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"d23a7eb05aa27080fe7ca0bc3a863c75\",\n" +
                    "AddOn: \"1506690878000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"e59cf249b66b3f9710c98af2f30b84c d23a7eb05aa27080fe7ca0bc3a863c75\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2931790\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"1\",\n" +
                    "ImageUrl: \"https://shawetcanada.files.wordpress.com/2017/09/20170413_zaa_nc34_030.jpg?quality=80&strip=all\",\n" +
                    "ArticleHash: \"d9a4c37cec0bea642f3935e7ea3c61d4\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"longroom_entertainment\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"While the fall 2017 season has only just begun, 2018 is already shaping up to megastar year. In addition to The Cher Show and Pretty Woman: The Musical, musical...ET Canada52 other people are viewing this story50 Top Stories Breaking Now!Where The World Finds Its News - LongRoom News!Wake Up To Breaking News!\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"6\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"longroom.com\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"be59cf249b66b3f9710c98af2f30b84c\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1544025600000\",\n" +
                    "Url: \"https://www.longroom.com/discussion/705673/a-musical-based-on-cher-s-life-headed-to-broadway\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1033\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2931805\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"A Musical Based On Cher’s Life Headed To Broadway\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"0d16d11377b26657e399d67adbf35da4\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"147f98a676e62c28b8e1dc5e3d5b5750\",\n" +
                    "MD2: \"05d20f9fa0299b9ebc158e24998cc536\",\n" +
                    "_route_: \"shard5\",\n" +
                    "_version_: 1583311552823427000,\n" +
                    "GroupName: \"AlandsRadioTV\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"ee5dad0ed789fec1828a5ce813296cd0\",\n" +
                    "AddOn: \"1507174784000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"b341fb338881d5ce06ad7cf5870af1e ee5dad0ed789fec1828a5ce813296cd0\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2779972\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"91fdb3eb65fdbc69e9fdd3bb1c60fe32\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"AlandsRadioTV_nyheter\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"Det är de fyra möllorna i Lemland Knutsboda, Frans, Fortuna, Freja och Fredrika, samt Mika på Kökar. Om ett år är ytterligare fyra vindkraftverk för gamla för det nödvändiga produktionsstödet, Altai på Vårdö, Fursten och Gideon i Finström Pettböle, samt Oskar på Sottunga. I dag är 19 vindkraftverk i gång på Åland och beräknas stå för cirka 20 procent av hela Ålands årliga elförbrukning. De fem vindkraftverk som denna höst blir för gamla för stöd, har nu beviljats en sista dusör av landskapsregeringen, cirka 10.000 euro per styck.\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"25\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"radiotv.ax\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"cb341fb338881d5ce06ad7cf5870af1e\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1543766400000\",\n" +
                    "Url: \"http://www.radiotv.ax/nyheter/vindkraft-faller-aldersstrecket\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1035\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2779976\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Vindkraft faller för åldersstrecket\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"b441aee2cdeb39eae36a51539496b3cb\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"7daa0635852efe05a0082ff637b24074\",\n" +
                    "MD2: \"26e197990e69c1ab6050b670b411d2e3\",\n" +
                    "_route_: \"shard3\",\n" +
                    "_version_: 1583311461593120800,\n" +
                    "GroupName: \"AlandsRadioTV\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"d5221f07f14dd40c8d832431360bc766\",\n" +
                    "AddOn: \"1507174784000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"370120312d1fb73baa52994246717ac d5221f07f14dd40c8d832431360bc766\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2779972\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"76455a750f28bcce31d3700ffccf70b8\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"AlandsRadioTV_nyheter\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"Evenemanget har temat ”Åland – ett framtidsperpektiv” och det hålls i Alandica den 24 oktober och ska belysa Åland i dag och i framtiden ur ett globalt, lokalt, identitetsmässigt och säkerhetspolitiskt perspektiv. Talare ärJan Sundberg, professor i statslära vid Helsingfors universitet,Christian Pleijel, programdirektör vid KTH i Stockholm och generalsekreterare för European Small Islands Federation,Hedda B. Langemyr,tidigare verksamhetsledare för Norges fredsråd ochBarbro Sundback, fredsinstitutets tidigare ordförande.\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"25\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"radiotv.ax\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"0370120312d1fb73baa52994246717ac\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1543766400000\",\n" +
                    "Url: \"http://www.radiotv.ax/nyheter/alands-framtid-diskuteras-pa-jubileum\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1035\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2779976\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Ålands framtid diskuteras på jubileum\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"341d2d448e9416453563f7d72a535120\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"3adcce04847188f49b426179a61cc2da\",\n" +
                    "MD2: \"0b12eeaf2de9b7f3e282604e64e24aea\",\n" +
                    "_route_: \"shard3\",\n" +
                    "_version_: 1583311577898025000,\n" +
                    "GroupName: \"AlandsRadioTV\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"c33454de886a0f6e73515f99aaffc7d8\",\n" +
                    "AddOn: \"1507172185000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"fc1e802d99d975b1a83184e68bd9c0d c33454de886a0f6e73515f99aaffc7d8\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2779972\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"0\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"476069f4e7ad4db11e52a4263c25f82c\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"AlandsRadioTV_nyheter\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"Det framkommer efter en enkätundersökning som social- och hälsovårdsinspektör Mikael Granholm har gjort. Enkäten gjordes efter att Ålands handikappförbund lämnat in en anmälan till ÅMHM där dom ville att myndigheten skulle ta reda på om det fanns brister i kommunernas service till personer med osynliga funktionsnedsättningar och speciellt till personer med psykisk ohälsa. Enkäten visar att över 60 procent av kommunerna erbjuder service till minst en person med psykisk ohälsa, men enkäten visar inte på hur många personer det rör sig om totalt. - Vår rekommendation är att kommunerna i större utsträckning ska använda sig av speciallagstiftning för klienterna, säger Mikael Granholm.\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"25\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"radiotv.ax\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"afc1e802d99d975b1a83184e68bd9c0d\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1543766400000\",\n" +
                    "Url: \"http://www.radiotv.ax/nyheter/se-over-rutiner-arbetssatt\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1035\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2779976\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Se över rutiner för arbetssätt\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"b24b129ec28cbeefd7339a699d9ad11e\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "},\n" +
                    "{\n" +
                    "ID: \"da7e3db8e306f6a1235f32a7a06ef05a\",\n" +
                    "MD2: \"a63aad640e066a9541747023441600ac\",\n" +
                    "_route_: \"shard2\",\n" +
                    "_version_: 1583316620787318800,\n" +
                    "GroupName: \"AlandsRadioTV\",\n" +
                    "IsVideo: \"0\",\n" +
                    "Pureadj: \"aa47f8a390bce941aebcedf8ea171f37\",\n" +
                    "AddOn: \"1507212083000\",\n" +
                    "PR: \"0\",\n" +
                    "WarnsLast: \"\",\n" +
                    "Companies: \"\",\n" +
                    "Overseas: \"1\",\n" +
                    "Product: \"\",\n" +
                    "GarbageWords: \"\",\n" +
                    "CustomerID: \"\",\n" +
                    "DefinedType: \"0\",\n" +
                    "Hash: \"5e12b91ed5876f0cd19e34d406b07d7 aa47f8a390bce941aebcedf8ea171f37\",\n" +
                    "ParagraphHash: \"\",\n" +
                    "GroupID: \"2779972\",\n" +
                    "Negative: \"0\",\n" +
                    "Headline: \"2\",\n" +
                    "WarnsOne: \"\",\n" +
                    "DefinedSite: \"\",\n" +
                    "Work: \"\",\n" +
                    "WarnsFive: \"\",\n" +
                    "Tags: \"\",\n" +
                    "QQ: \"\",\n" +
                    "Views: \"0\",\n" +
                    "Channel: \"0\",\n" +
                    "HighWarns: \"\",\n" +
                    "Images: \"0\",\n" +
                    "ImageUrl: \"\",\n" +
                    "ArticleHash: \"f9ec00c4d5420d4107c40ccc1aebfaa0\",\n" +
                    "WarnsFifteen: \"\",\n" +
                    "TaskName: \"AlandsRadioTV_home\",\n" +
                    "WarnsThree: \"\",\n" +
                    "Content: \"Den 3 december 2018 ska dockan vara klar och några månader senare kan Pommern fortsätta som turistattraktion i anslutning till sjöfartsmuseet. Leif Ahlqvist, vd för Mariehamns hamn Ab, tycker att det är skönt att bygget har kommit igång på allvar.\",\n" +
                    "HotChannel: \"0\",\n" +
                    "Nounnum: \"\",\n" +
                    "Author: \"\",\n" +
                    "Country: \"25\",\n" +
                    "SCatagory: \"0\",\n" +
                    "TopicID: \"0\",\n" +
                    "Domain: \"radiotv.ax\",\n" +
                    "IsTopic: \"0\",\n" +
                    "Person: \"\",\n" +
                    "Pure: \"e5e12b91ed5876f0cd19e34d406b07d7\",\n" +
                    "Heading: \"0\",\n" +
                    "Time: \"1543766400000\",\n" +
                    "Url: \"http://www.radiotv.ax/aland-idag/bygget-pommerndockan-har-borjat\",\n" +
                    "Tissue: \"0\",\n" +
                    "Language: \"1035\",\n" +
                    "Praises: \"0\",\n" +
                    "MatchHash: \"\",\n" +
                    "KeyPOS: \"\",\n" +
                    "SiteType: \"17\",\n" +
                    "WarnsTen: \"\",\n" +
                    "Departments: \"\",\n" +
                    "SiteID: \"0\",\n" +
                    "TaskID: \"2779974\",\n" +
                    "Keyword: \"\",\n" +
                    "Comments: \"0\",\n" +
                    "Pun: \"\",\n" +
                    "Title: \"Bygget av Pommerndockan har börjat\",\n" +
                    "From: \"\",\n" +
                    "EMail: \"\",\n" +
                    "LowWarns: \"\",\n" +
                    "Continent: \"0\",\n" +
                    "StartHash: \"52c10ac76be39ccf280acc1e6ffde915\",\n" +
                    "IsGarbage: \"0\",\n" +
                    "MiddleWarns: \"\",\n" +
                    "Positive: \"0\",\n" +
                    "SChannel: \"0\",\n" +
                    "Brands: \"\",\n" +
                    "CellPhoneNum: \"\",\n" +
                    "Tag: \"\",\n" +
                    "Place: \"\"\n" +
                    "}\n" +
                    "],\n" +
                    "status: 0\n" +
                    "}";
            //本地测试
            //System.out.println(httpUtil.sendPost("http://localhost:18797/index",string));

            //hbsw32测试
            System.out.println(httpUtil.sendPost("http://172.16.4.32:18797/index",string));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //}
    }
}