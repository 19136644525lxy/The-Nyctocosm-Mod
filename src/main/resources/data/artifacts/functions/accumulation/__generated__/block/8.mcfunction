#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove a.accumulation.active
scoreboard players operation @s m.global.tracechance.modifier -= @s a.accumulation.probMod
scoreboard players reset @s a.accumulation.probMod