package data

import models._
import org.scalatest.{ Matchers, FlatSpec }

class RolesTest extends FlatSpec with Matchers {

  it should "find all transitive dependencies" in {
    val r0 = RoleSummary(RoleId("id0"), Set(RoleId("id1"), RoleId("id2")), null, null)
    val r1 = RoleSummary(RoleId("id1"), Set(RoleId("id2")), null, null)
    val r2 = RoleSummary(RoleId("id2"), Set(RoleId("id3")), null, null)
    val r3 = RoleSummary(RoleId("id3"), Set.empty, null, null)

    val roles = List(r0, r1, r2, r3)
    Roles.transitiveDependencies(roles, r0) should contain only (RoleId("id1"), RoleId("id2"), RoleId("id3"))
  }

  it should "return direct dependencies if there are no transitive" in {
    val r0 = RoleSummary(RoleId("id0"), Set(RoleId("id1"), RoleId("id2")), null, null)
    val r1 = RoleSummary(RoleId("id1"), Set(RoleId("id2")), null, null)
    val r2 = RoleSummary(RoleId("id2"), Set.empty, null, null)
    val r3 = RoleSummary(RoleId("id3"), Set(RoleId("id2")), null, null)

    val roles = List(r0, r1, r2, r3)
    Roles.transitiveDependencies(roles, r0) should contain only (RoleId("id1"), RoleId("id2"))
  }

  it should "return empty if there are no dependencies" in {
    val r0 = RoleSummary(RoleId("id0"), Set(RoleId("id1"), RoleId("id2")), null, null)
    val r1 = RoleSummary(RoleId("id1"), Set(RoleId("id2")), null, null)
    val r2 = RoleSummary(RoleId("id2"), Set.empty, null, null)
    val r3 = RoleSummary(RoleId("id3"), Set(RoleId("id2")), null, null)

    val roles = List(r0, r1, r2, r3)
    Roles.transitiveDependencies(roles, r2).isEmpty should be (true)
  }
}
