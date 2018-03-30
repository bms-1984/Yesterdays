package net.bms.yesterdays.cap

class DefaultSoul: ISoul {
    override var soulType: EnumSoulType = EnumSoulType.SHUDRA
    override var killCount: Int = 0
    override var hasReachedMoksha: Boolean = false
    override var livesLived: Int = 0
}