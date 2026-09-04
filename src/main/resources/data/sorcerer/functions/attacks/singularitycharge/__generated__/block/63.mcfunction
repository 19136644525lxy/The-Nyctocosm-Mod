#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.singularitychargetimer 1
tag @s[scores={ns.singularitychargetimer=100..}] add explode
function effects:void_swirl/single_reverse
particle dust 0 0 0 3 ~ ~ ~ 0.3 0.3 0.3 0 5 force
tp @s ^ ^ ^0.3
execute at @s anchored eyes facing entity @a[gamemode=!spectator,sort=nearest,distance=..40] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^5 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
playsound nyctocosm:sorcerer.singularitycharge.hum master @a[distance=..30] ~ ~ ~ 1
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add explode
execute if entity @a[distance=..3,gamemode=!spectator] run tag @s add explode
execute as @s[tag=explode] run function sorcerer:attacks/singularitycharge/explode
execute if entity @e[tag=spell,tag=reflector,distance=..3] run scoreboard players operation TEMP s.damage = singularitychargedamage value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function sorcerer:attacks/singularitycharge/__generated__/block/64
execute rotated as @e[tag=spell,tag=reflector,distance=..3] run tp @s ^ ^ ^0.3 ~ ~