package com.threedays.domain.user.entity

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.introspector.PrimaryConstructorArbitraryIntrospector
import com.threedays.domain.user.vo.Gender
import com.threedays.domain.user.vo.LocationId
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Year

@DisplayName("[도메인] - 유저")
class UserTest : DescribeSpec({

    val fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
        .objectIntrospector(PrimaryConstructorArbitraryIntrospector.INSTANCE)
        .build()


    describe("유저 생성") {
        it("새로운 유저를 생성한다") {
            // arrange
            val name: User.Name = fixtureMonkey.giveMeBuilder<User.Name>().sample()
            val userGender: Gender = fixtureMonkey.giveMeBuilder<Gender>().sample()
            val userBirthYear: Year = fixtureMonkey.giveMeBuilder<Year>().sample()
            val userCompany: Company = fixtureMonkey.giveMeBuilder<Company>().sample()
            val userJob: Job = fixtureMonkey.giveMeBuilder<Job>().sample()
            val userLocations: List<LocationId> = fixtureMonkey.giveMe<LocationId>(3)
            val partnerBirthYearRange: ClosedRange<Year> =
                fixtureMonkey.giveMeBuilder<ClosedRange<Year>>().sample()
            val partnerJobOccupations: List<Job.Occupation> = fixtureMonkey.giveMeBuilder<Job.Occupation>().sampleList(10)
            val partnerPreferDistance: UserDesiredPartner.PreferDistance =
                fixtureMonkey.giveMeBuilder<UserDesiredPartner.PreferDistance>().sample()

            // act
            val user = User.create(
                name = name,
                userGender = userGender,
                userBirthYear = userBirthYear,
                userCompanyId = userCompany.id,
                userJobId = userJob.id,
                userLocationIds = userLocations,
                partnerBirthYearRange = partnerBirthYearRange,
                partnerJobOccupations = partnerJobOccupations,
                partnerPreferDistance = partnerPreferDistance,
            )

            // assert
            user.id shouldNotBe null
            user.name shouldBe name
            user.profile.gender shouldBe userGender
            user.profile.birthYear shouldBe userBirthYear
            user.profile.companyId shouldBe userCompany.id
            user.profile.jobId shouldBe userJob.id
            user.profile.locationIds shouldBe userLocations
            user.desiredPartner.birthYearRange shouldBe partnerBirthYearRange
            user.desiredPartner.jobOccupations shouldBe partnerJobOccupations
            user.desiredPartner.preferDistance shouldBe partnerPreferDistance
        }
    }


})
