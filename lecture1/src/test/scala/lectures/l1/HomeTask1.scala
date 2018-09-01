import org.scalatest.{FlatSpec, Matchers}

object CountRoundToLower{
  def undefined() = throw new IllegalArgumentException("*.5")

  def toHigh(v:Float):Float = {
    v * 10 % 10
  }

  def counter(toUp:Float):Int= {
    if(toUp >= 6) return 0
    if(toUp >= 0 && toUp < 5) return 1
    if(toUp > (-5) && toUp < 0) return 0
    if(toUp <= (-6)) return 1
    undefined()
  }
  def count(list:Seq[Float]) = list.map(v => counter(toHigh(v))).sum
}

class CountRoundToLowerTest extends FlatSpec with Matchers {

  import CountRoundToLower.count

  it should "count numbers" in {
    count(Seq(1, 2l, 1.1f)) shouldBe 3
    count(Seq(1.3f, 1.6f)) shouldBe 1
  }

  it should "throw error" in {
    assertThrows[IllegalArgumentException](
      count(Seq(1, 1.6f, 1.5f))
    )
  }

  it should "support big long number" in {
    count(Seq(1000000000l)) shouldBe 1
  }
}