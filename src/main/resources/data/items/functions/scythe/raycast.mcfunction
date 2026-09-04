#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s dash_range 1
execute if score @s dash_range matches 7.. run tag @s add enddash
execute positioned ^ ^ ^1 if block ~ ~ ~ #nyctocosm:impenetrable run tag @s add enddash
execute positioned ^ ^ ^1 if block ~ ~1 ~ #nyctocosm:impenetrable run tag @s add enddash
particle dust 0 0 0 2 ~ ~1 ~ 0.2 0.4 0.2 0.1 50 force
tp @s ~ ~ ~
tag @s add self
execute positioned ~ ~1 ~ as @e[type=#nyctocosm:livingthings,tag=!self,distance=..2,tag=!scythehit] run function items:scythe/__generated__/block/30
execute as @s[tag=!enddash] positioned ^ ^ ^1 run function items:scythe/raycast
execute as @s[tag=enddash] run function items:scythe/__generated__/block/37
tag @s remove self