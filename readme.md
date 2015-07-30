运行主要文件：GenAcronym/src/main/genTest.java 输出20个缩略词选项。推荐系统没加上去，因为JWI是在只支持windows上的环境。<br>
GenAcronym/<br>
    *GenAcronym/edu.mit.jwi_2.3.3_jdk.jar和GenAcronym/commons-logging.jar:运行wordNet同义词查询必须的jar包<br>
GenAcronym/LAD_result<br>
    *GenAcronym/LAD_result/ComClass.txt:每个单词在每个topic中的排名，每行是单词和30个数字
    *GenAcronym/LAD_result/ComVocabStop.txt:计算机领域从abstract提取出的去除停用词的词（不一定是单词）
    *GenAcronym/LAD_result/ComPapStop.txt:LDA需要的文件格式，用于训练
    *GenAcronym/LAD_result/Com_all.txt:计算机领域abstract利用LDA分成30topic，每一topic都是142582单词根据相关排序<br>
GenAcronym/dic<br>
    *GenAcronym/dic/index_del.adj:从wordNet中提取出的是形容词的单词
    *GenAcronym/dic/index_del.adv:从wordNet中提取出的是副词的单词
    *GenAcronym/dic/index_del.verb:从wordNet中提取出的是动词的单词
    *GenAcronym/dic/index_del.noun:从wordNet中提取出的是名词的单词
    *GenAcronym/dic/index_del.prep:一些介词
    *GenAcronym/dic/index_del.sense:单词的一些变化<br>
GenAcronym/src:代码部分<br>
    *GenAcronym/src/io:TextReader.java读文件的api
                  *TextWriter.java写文件的api
    *GenAcronym/src/changeWord:wordNet返回同义词的api（先安装WordNet，设置环境变量WNHOME，指向WordNet的安装根目录。例如：WNHOME = “E:\Commonly Application\WordNet\2.1”；）
    *GenAcronym/src/LDA_file_deal:comPapaer.java（训练处理文件，有文章处理成LDA能运行的格式。）GetVocab.java（提取单词。）
    *GenAcronym/src/stemmer:波特词根算法
    *GenAcronym/src/Trie:Trie树数据结构
    *GenAcronym/src/gen_acronym:生成缩略词和缩略词排序代码。AcronymSort.java（缩略词排序程序）Gen.java（缩略词生成函数）<br>

=====================================================================================

下面主要是之前的一些尝试，和主要程序无关<br>
缩略词分类/:在CiteSeerx上查找的含缩略词的分类<br>
SearchCrawler/CiteSeerx_search.java:在CiterSeerx上爬虫寻找文章标题程序<br>
GetPaperName/：采用LSI获取缩略词的方式<br>
GenAcronym/src/handler：采用K-means分类的方式，包含利用TF-idf训练的方法<br>
