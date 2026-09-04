#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~ ~ 1 1 1 0 5 force
particle flash ~ ~ ~ 1 1 1 0 5 force
execute at @s run playsound nyctocosm:emissary.attack.pillars.impact_cloud master @a[distance=..50] ~ ~ ~ 10
effect give @s blindness 2 0 true
effect give @s slowness 2 2 true
scoreboard players operation @s ncd_damage = pillarsdamage em.value
scoreboard players operation @s ncd_damage *= 1000 value
scoreboard players operation damageAmt value = @s ncd_damage
tag @e[tag=emissary,tag=emissarymodel,limit=1] add damager
execute as @s run function emissary:attacks/phase1/pillars/__generated__/block/321
tag @s remove em.impact.cloud