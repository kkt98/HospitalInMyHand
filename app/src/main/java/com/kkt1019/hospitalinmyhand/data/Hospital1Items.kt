import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
class Hospital1Items @JvmOverloads constructor(
    @field:Element(name = "header", required = false)
    var header: Header? = null,

    @field:Element(name = "body", required = false)
    var body: Body? = null
)

@Root(name = "header", strict = false)
class Header @JvmOverloads constructor(
    @field:Element(name = "resultCode", required = false)
    var resultCode: String? = null,

    @field:Element(name = "resultMsg", required = false)
    var resultMsg: String? = null
)

@Root(name = "body", strict = false)
class Body @JvmOverloads constructor(
    @field:ElementList(entry = "item", inline = true, required = false)
    var items: ResponseItme? = null,

    @field:Element(name = "numOfRows", required = false)
    var numOfRows: Int? = null,

    @field:Element(name = "pageNo", required = false)
    var pageNo: Int? = null,

    @field:Element(name = "totalCount", required = false)
    var totalCount: Int? = null
)

@Root(name="items")
class ResponseItme @JvmOverloads constructor(
    @field:ElementList(inline = true)
    var item:List<Item>?=null
)

@Root(name = "item", strict = false)
class Item @JvmOverloads constructor(
    @field:Element(name = "dutyAddr", required = false)
    var dutyAddr: String? = null,

    @field:Element(name = "dutyDiv", required = false)
    var dutyDiv: String? = null,

    @field:Element(name = "dutyDivNam", required = false)
    var dutyDivNam: String? = null,

    @field:Element(name = "dutyEmcls", required = false)
    var dutyEmcls: String? = null,

    @field:Element(name = "dutyEmclsName", required = false)
    var dutyEmclsName: String? = null,

    @field:Element(name = "dutyEryn", required = false)
    var dutyEryn: String? = null,

    @field:Element(name = "dutyName", required = false)
    var dutyName: String? = null,

    @field:Element(name = "dutyTel1", required = false)
    var dutyTel1: String? = null,

    @field:Element(name = "dutyTime1c", required = false)
    var dutyTime1c: String? = null,

    @field:Element(name = "dutyTime1s", required = false)
    var dutyTime1s: String? = null,

    @field:Element(name = "dutyTime2c", required = false)
    var dutyTime2c: String? = null,

    @field:Element(name = "dutyTime2s", required = false)
    var dutyTime2s: String? = null,

    @field:Element(name = "dutyTime3c", required = false)
    var dutyTime3c: String? = null,

    @field:Element(name = "dutyTime3s", required = false)
    var dutyTime3s: String? = null,

    @field:Element(name = "dutyTime4c", required = false)
    var dutyTime4c: String? = null,

    @field:Element(name = "dutyTime4s", required = false)
    var dutyTime4s: String? = null,

    @field:Element(name = "dutyTime5c", required = false)
    var dutyTime5c: String? = null,

    @field:Element(name = "dutyTime5s", required = false)
    var dutyTime5s: String? = null,

    @field:Element(name = "dutyTime6c", required = false)
    var dutyTime6c: String? = null,

    @field:Element(name = "dutyTime6s", required = false)
    var dutyTime6s: String? = null,

    @field:Element(name = "hpid", required = false)
    var hpid: String? = null,

    @field:Element(name = "wgs84Lat", required = false)
    var wgs84Lat: String? = null,

    @field:Element(name = "wgs84Lon", required = false)
    var wgs84Lon: String? = null
)

