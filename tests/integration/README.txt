Shield of Cthulhu integration regression

ShieldRegression.java is a temporary NeoForge test mod. Do not install it on a public server.
Compile with Java 21 against a Minecraft 1.21.1 / NeoForge 21.1.250 server plus Fargo's Talismans,
Curios, and their dependencies. Package the compiled classes with this META-INF/neoforge.mods.toml:

modLoader="javafml"
loaderVersion="[1,)"
license="MIT"
[[mods]]
modId="shieldregression"
version="1.0"
displayName="Shield Regression Tests"

Run a disposable dedicated server bound to localhost with this test jar. Execute
shieldregression from its console or RCON. It creates a FakePlayer with guardian
damage enabled and equips the real shield through Curios. It calls the actual
shield handler; the guardian's real hurt implementation and the NeoForge event
bus supply the nested retaliation path.

Expected unpatched result: REPRODUCED: guardian/shield StackOverflowError.
Expected patched result: PASS, including bounded guardian retaliation, normal damage
and knockback, later independent hits, cleanup after a throwing damage callback,
and no shield damage when not sprinting.

The test player and entities are disposable and never added to the player list.
The test clears the equipped item even when the control reproduces the overflow.
