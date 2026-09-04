#built using mc-build (https://github.com/mc-build/mc-build)

function animated_java:ajemissary/remove/all
execute as @e[type=slime,tag=emissary] run data merge entity @s {Size:0}
kill @e[tag=emissary]
scoreboard players reset Emissary em.phase
scoreboard players reset Emissary em.health
scoreboard players reset EmissaryInvulnerable em.value
scoreboard players reset Attacking em.value
scoreboard players reset Idle em.value
scoreboard players reset Flying em.value
scoreboard players reset Poise em.value
scoreboard players reset MusicPhase em.phase
bossbar set minecraft:emissary players
execute as @a[tag=music.emissary] run function music:emissary/end
tag @a remove em.damagedemissary