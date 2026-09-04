#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add a.protection_dissonance.active
scoreboard players operation a.TempResMod a.value = @s m.hypertrace.charge
scoreboard players operation a.TempResMod a.value *= a.protection.resAmt a.value
scoreboard players operation @s a.protection.dissonanceMod = a.TempResMod a.value
scoreboard players operation @s m.dissonance.resistance.modifier += a.TempResMod a.value