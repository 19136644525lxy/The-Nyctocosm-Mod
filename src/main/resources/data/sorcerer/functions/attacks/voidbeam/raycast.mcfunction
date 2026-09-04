#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.voidbeamrange 1
execute if score @s ns.voidbeamrange matches 60.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add endraycast
execute rotated ~ ~-90 run function effects:sorcerer/halobeambeam
execute if entity @e[tag=spell,tag=reflector,tag=!matchedid,distance=..3] run scoreboard players operation TEMP s.damage = voidbeamdamage value
execute as @e[tag=reflector,tag=spell,distance=..3] at @s run function sorcerer:attacks/voidbeam/__generated__/block/133
execute positioned ~-0.25 ~-0.25 ~-0.25 as @a[dx=0] positioned ~-0.5 ~-0.5 ~-0.5 if entity @s[dx=0] run function sorcerer:attacks/voidbeam/hit
execute if entity @a[tag=voidbeamhit] run function sorcerer:attacks/voidbeam/__generated__/block/134
execute as @s[tag=endraycast] run function sorcerer:attacks/voidbeam/__generated__/block/135
execute as @s[tag=!endraycast] unless entity @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^0.5 run function sorcerer:attacks/voidbeam/raycast
execute as @s[tag=!endraycast] rotated as @e[tag=spell,tag=reflector,distance=..3,sort=nearest,limit=1] positioned ^ ^ ^0.5 run function sorcerer:attacks/voidbeam/raycast