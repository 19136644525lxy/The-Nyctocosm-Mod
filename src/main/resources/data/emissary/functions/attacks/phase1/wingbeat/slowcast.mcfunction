#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.value 1
execute if score @s em.value matches 200.. run tag @s add end
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = wingbeatdamage em.value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function emissary:attacks/phase1/wingbeat/__generated__/block/78
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ~ ~ ~ ~ ~
execute at @s run tp @s ^ ^ ^1
execute at @s run function emissary:attacks/phase1/wingbeat/__generated__/block/79
execute at @s unless block ~ ~ ~ #nyctocosm:passthrough run function emissary:attacks/phase1/wingbeat/__generated__/block/280
execute at @s if entity @a[gamemode=!spectator,tag=!emissarydebug,tag=!s.immoveable,distance=..5] run function emissary:attacks/phase1/wingbeat/__generated__/block/281