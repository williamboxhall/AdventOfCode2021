fun main() {
    println(Day9().risk(day9Input))
}

class Day9 {
    fun risk(matrix: List<List<Int>>): Int {
        println(matrix.toPrintableString())
        val horizontalMinPoints = horizontalMinPoints(matrix, false)
        val verticalMinPoints = horizontalMinPoints(matrix.transpose(), true)
        val intersectingMinPoints = horizontalMinPoints intersect verticalMinPoints
        return intersectingMinPoints.map { it.h + 1 }.sum()
    }

    private fun horizontalMinPoints(matrix: List<List<Int>>, flipXY: Boolean) = matrix.flatMapIndexed { yIndex, row ->
        (row + Int.MAX_VALUE).windowed(2).foldIndexed(Accumulator(emptyList(), Int.MAX_VALUE)) { xIndex, acc, (prev, current) ->
            if (current > prev) {
                if (prev < acc.lastMinHeight) {
                    val newPoint = if (flipXY) Point(yIndex, xIndex, prev) else Point(xIndex, yIndex, prev)
                    acc.copy(points = acc.points + newPoint, lastMinHeight = prev)
                } else {
                    acc
                }
            } else {
                acc.copy(lastMinHeight = Int.MAX_VALUE)
            }
        }.points
    }
}

val day9Input = """5457889101989999876543210123598932197856889569898943293299789965432998765467954321986569876545679101
4345678919879898989874322234567894396545679698767899989987678899549899878357895210975498765434589213
3234589998764767998765433795688975985432798999545678978976567678998789989456976329864329886545678924
2123459987653459899887654689789899876521287789637889767895434567897699896567899439643212987656789545
4012348998432398776998765799898779765430345678923999858954323458924598798679978998768103499967899656
2123456799210987655349889892976569864321239789414896545995512388912987679789467897654215679878998769
3234767989629876544236999901965456975432398996325789234789623567899876569894356798785676789989999878
5349879876536965432125478919873201989865467895459896545678934878998987456965267889876787892196789989
6657989989545988541012367899954332399977578976567987876789656789987654349872123678989898943235678999
7789197898656987652143456998765443478998989999878998987898767991299543234983234589999999654345899989
8893256798767898763234589769976954567899899988989999898909898989398954123495665799899899985456798678
9999345679878939876345678954989865779976789977899988789212999878987892034976786989798799876569987569
2398959789989321965498789543596989893565679856798767678924598767976793156799899878587678987698765467
3987899999898999876569996432345699921434798743987657567895679854865789967893998765436569998989876567
9876789898787989987689876541236789210123987632399543458976798543234597899932398654323478909876987678
6545698766545679998789998742345678921234596543988432357997987652123456978921239973212348919765499889
9656987657324788999992129853456799534999987784976721236789999761012878967892398765343456898754343996
8997898543213767893459012994967899649887898899885830245899989875434589658789999876774567999843212345
7889909654104456932998923989898998998656569998764321346789876976545697545567897988965778987659101256
5678919798412367899887899878789987899643456789965475457898765989676789432459976599876789499798932387
4559899987323456789756998768678976789432345899876876568999876798987894321298995432987893298987943598
3446789876535567897545698654567895678910196789987898879999989897898976410987989943698954987896894789
2135678987676698976523987643789954578922989998998949989989994946789654329896778894569769876685789991
4576799598989789765435698755678943458939878887899656997678943235998785498765456789999898765434567890
5689895409499899876546798767789656567898766776789769874589432129899896599854345676789999984323456789
6799974312347999987656899878998798678987655575899998765696541098785987987943234545689998543012347895
7898765423456789898967945989439899889876543454689899876789652987654598996432101235678987652125456954
8929876567867896789878939896323920998765442123456789987898543998767679999545237897789876543434587893
9213987678879975698989498765434591239754321012367890198987659899978789987656346789896987656568998932
2102798999989454987692349976596789998765672123478989999998798789989893498768458999995498897789679321
3215699989994343598543568989987899879876543234679678899999987678999932349879569328789349998894598762
4324989878943232349694567895698910965987654345789556789899976545989321278998678915678998769923459853
5439876767893101298989678934999329877998765459994345698799875439878932567899989434589897543214598767
7598765656964567987978989129878934989899887598943235987679876798767893459902496545698789654323459898
8987654349895879896867899998769895798789999987899102398589987987656789567893987667987678965456967989
9876543234789998765456789899658789989676899876778993459456798998987897678954598989876567896567899877
8987894123678987654347896789745679877565789995467889569323459999698998999997699998765457899678998766
7998963234589998743212345678934599965434678984345678998909567894589989889989789989874356788989987655
6987654345678999854323489789125987654323459876289789987698978923679879779878999876543249867899876534
5498775456789999765435678999939876543212598765178999876567899219798765665769899995454123456789764323
4309886578996789986545789579899987654101987654068999985458965498999854523456789876321012347997653213
3212997689345996797656895498789876543212398743256789876578976987987643212345999865432134469998754324
4323498793239895698787976987678988665425459754345896997699989876798932101256895976654346567899765436
5634599892109789999898997896567999776434769865456794598789998765679893212367914987765457878999896597
6545987989997679896969898995458999889556878976567893499899987674398765324578923599876768989989987698
7659876567896598765456789884367899998667999987878912987999896543219898545689894999989889595678998999
8767985456789459876345798765456789098788998698989893976789789654102987676798789878999995434578999999
9879894348992345987212349878567892199999769439396789765345698765233498987897678767898765323467899989
5998763238943456992101299989678999989898954321245899854236789654344579398978569656789875412567999878
3349654367894569894323989993989998776787895410123989969499999875696989299965456345698983101278987767
2198767456987698765459878932199989545476789321234568998988921999987892987874321245897893294389876553
9099876567899789986598767891019878234345679632345678997877890987998921976543210767896894989499965432
8986987678998990297987758989997967101234789543489989876466789876789890987654321459965989878999874321
7855698789987894379876545879876543242945678964568998694355699765776789998765432368979978767999976430
6543239899876789567965434567989854356856789978789998543234988654565678999986543459998767656789984321
7684102998775698979854323658998765678967899989895987654349876543334567899898654567899656345678965432
9873213987654567898783012349999976789998999994954598765656988432125678998789765679998543234569876545
8764329896543456789652123456894399898999598995963699876767899563234889687678976789987654347678987676
9965498765432347896543234599943201967895497789892987987898987654345796444569997998798987656789798987
9876789887654556987754345988956912356789345698790976599939698765456789323458998987659198767897679998
8987898998765677898965459767999895457891234987689865432123569976887893212357899896541019878955567999
7698997999987898949996569856989789769910149876545954321012397987998984623768956789432199989543456789
6549656891299999234989679879878678978921298765434595932124986698999876544878949896593989995432345698
5432345993345692129878989998666569989432987655423989893239975549998989656889238789989878954321239987
9940137789456789998767899986543458997545698543210978789398754234976599867990145699768767995634398876
8893235678967899876656789997632567997656987654599865698999543145895445978921956798654356789785977645
7789545789878997985545679876541245689767898785987654986987658656789329989439898998543267899899864524
6678996799989876544234567989710355789978989876798765975498868787898998798998789987654378912998753212
4569989989993989734146456797651234567899878987899978994309989898957987657895678998767459901979865301
3656978878921298921012367898732345778998767898934989983212397999345799546234569999878567899869875412
2349865467932987432156778929843656989987656799323999876723456789239987632128998989987678987656996523
1098954359899876545245899219974768999876545678909898765436567890198997654267997878998989298543498434
2987643234767987656345998998765689998965434567898769986747698999987789795459886767899299129432599545
3499632127656798965456987899896795987654323689998656987656799998765678987598765656789198998921987656
4598532012345689876567896543987894599865464567896545799798899987654569898929854348899987987939999777
5987647123498991987789997674698923459876587678965434599899999878543234789439765249999996876898998989
6987656734567890298892398789789212355997898789534123989912398765432123678998764345678975575947896597
8998987845678979999901239899899201234798919894321019878901498754321044589989875476789864433236795456
9349598956789567899919348987978929395679323989535199769899598765432123799877986789897653210145689346
2102349767893456998898967896567898989798939978945988956798999876543434569766797899999754323234896556
3212459879912349887767898923458997678997898767899877546587998989754545678944698989998765434345789967
4343457989101249786656789912349876569876798954098765323456987899987678989433569678999887865456799878
5454599995212998655545679893998765478965987543129876454677896789398789894322454569899999976587898989
6569988994329877544434598799899876567894987675434987565789965689239898765410123698789898987679987695
7898877889934965432123987688789987678923498786645698976897654578999979876321245987698767898789876534
8997666578895696544019876577678998789012569897896789987976543467989763987443456798597656789899954323
9987543467789987652129765456567899892123478998998893498989432345678954597556567989432347998989965912
3696542576689998543298654325458999943234569879679912349998651234567899998667679878521238987678899893
4597321234579987656987543212347898659349698965569101299876543345678988898778798764210349878569789799
5696510123467898799875432101236789898998997973468993989987854697789876789899899875321459765437677678
6989423236567989989999543212345678987897876791569989876598965789899975698976945976732998754326534567
9876534345678978878898765423556789256986745989878978987679876789999864587895434989549876543210123456
5997845656789769867789876534667896347894331878989568998899997891298783475689923597678987656921434587
4398659867896546745678987865778965459973210168995467899998798920197652323457895989989499869896545698
3209897979987631236789998989889976569865322347894349902987679921987541013456789379892345998789676789
4399965989999746345678969890997987678976454456789278919996567892986432124567895266791259997679787898
5989654598899975458789656791976598989989876767890167898765456789196543235678954345890198786568998967
9878943656789876567896545689765429998795989979921256789876367899297654346789865676789987676467899456
9767892545678987678967434578965312989654393989432345678987245678998765457899876797899876543344568968
7659901234567899789654323489986329876543212396545456789994346789019876567901987898998765432123679879""".split("\n").map {
    it.toList().map { it.toString().toInt() }
}

data class Accumulator(val points: List<Point>, val lastMinHeight: Int)
data class Point(val x: Int, val y: Int, val h: Int)
