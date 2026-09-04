#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s anchored eyes facing entity @a[sort=nearest,distance=..50,gamemode=!spectator,limit=1] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^10 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
scoreboard players add ProCRange value 1
execute if score ProCRange value matches 3.. run tag @s add endcast
execute unless block ~ ~ ~ #nyctocosm:passthrough run function sorcerer:attacks/voidmeteor/impact
particle dust 0.741 0.000 1.000 2 ~ ~ ~ 0.4 0.4 0.4 0 2 force
particle dust 0 0 0 2 ~ ~ ~ 0.4 0.4 0.4 0 2 force
particle minecraft:campfire_signal_smoke ~ ~ ~ 0.3 0.3 0.3 0 1 force
particle smoke ~ ~ ~ 0.3 0.3 0.3 0 1 force
particle flame ~ ~ ~ 0.3 0.3 0.3 0.01 1 force
execute at @s[tag=!endcast] if entity @e[tag=spell,tag=reflector,distance=..3] run scoreboard players operation TEMP s.damage = voidmeteordamage value
execute at @s[tag=!endcast] as @e[tag=reflector,tag=spell,distance=..3] at @s run function sorcerer:attacks/voidmeteor/__generated__/block/123
execute as @s[tag=!endcast] unless entity @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^0.5 run tp @s ~ ~ ~ ~ ~
execute as @s[tag=!endcast] if entity @e[tag=spell,tag=reflector,distance=..3] rotated as @e[tag=spell,tag=reflector,distance=..3,sort=nearest,limit=1] positioned ^ ^ ^0.5 run tp @s ~ ~ ~ ~ ~
execute at @s[tag=!endcast] run function sorcerer:attacks/voidmeteor/procast
execute at @s[tag=endcast] run function sorcerer:attacks/voidmeteor/__generated__/block/124