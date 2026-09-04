#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s anchored eyes facing entity @a[sort=nearest,distance=..50,gamemode=!spectator,limit=1] feet positioned ^ ^ ^0.5 rotated as @s positioned ^ ^ ^5 facing entity @s eyes facing ^ ^ ^-1 positioned as @s run tp @s ~ ~ ~ ~ ~
scoreboard players add ProCRange value 1
execute if score ProCRange value matches 2.. run tag @s add endcast
execute unless block ~ ~ ~ #nyctocosm:passthrough run tag @s add kill
function sorcerer:attacks/frozenblades/hitbox
execute at @s[tag=!endcast] if entity @e[tag=spell,tag=reflector,distance=..3] run scoreboard players operation TEMP s.damage = frozenbladesdamage value
execute at @s[tag=!endcast] as @e[tag=reflector,tag=spell,distance=..3] at @s run function sorcerer:attacks/frozenblades/__generated__/block/81
execute as @s[tag=!endcast] unless entity @e[tag=spell,tag=reflector,distance=..3] positioned ^ ^ ^0.4 run tp @s ~ ~ ~ ~ ~
execute as @s[tag=!endcast] if entity @e[tag=spell,tag=reflector,distance=..3] rotated as @e[tag=spell,tag=reflector,distance=..3,sort=nearest,limit=1] positioned ^ ^ ^0.4 run tp @s ~ ~ ~ ~ ~
execute at @s[tag=!endcast] run function sorcerer:attacks/frozenblades/procast
execute at @s[tag=endcast] run function sorcerer:attacks/frozenblades/__generated__/block/82