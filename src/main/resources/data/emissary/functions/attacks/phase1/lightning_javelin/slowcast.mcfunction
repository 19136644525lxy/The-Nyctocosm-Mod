#built using mc-build (https://github.com/mc-build/mc-build)

execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = lightningjavelindamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase1/lightning_javelin/__generated__/block/51
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^1
execute at @s run particle lava ~ ~ ~ 0.4 0.4 0.4 0 1 force
execute at @s run particle flame ~ ~ ~ 0.3 0.3 0.3 0.05 1 force
execute if score @s em.value matches 60.. run tag @s add end
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase1/lightning_javelin/__generated__/block/52
execute at @s positioned ~-0.5 ~-0.5 ~-0.5 if entity @a[dx=0,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase1/lightning_javelin/__generated__/block/53