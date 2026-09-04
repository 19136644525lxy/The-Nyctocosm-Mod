#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s run particle dust 0.561 0.000 0.000 0.5 ~ ~ ~ 5 5 5 0 25 normal
execute at @s run particle dust 0.000 0.000 0.000 1 ~ ~1 ~ 0.6 0.6 0.6 0 1 force
scoreboard players add @s floattimer 1
execute if score @s floattimer >= @s floatmax run function nyctocosm:mobs/nightmare_apparition/__generated__/block/16
execute as @s[tag=float_up] run tp @s ~ ~0.005 ~
execute as @s[tag=!float_up] run tp @s ~ ~-0.005 ~
scoreboard players add @s soundtimer 1
execute as @s[scores={soundtimer=8..}] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/18
execute at @s unless entity @a[distance=..200] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/19
execute at @s facing entity @a[distance=..20,sort=nearest,limit=1] feet run tp @s ~ ~ ~ ~ ~
execute at @s[tag=!alerted] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/20
execute at @s[tag=!alerted] if entity @a[gamemode=survival,distance=..13,predicate=!nyctocosm:is_sneaking,tag=walk] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/21
execute at @s[tag=!alerted] if entity @a[gamemode=survival,distance=..13,predicate=nyctocosm:is_sprinting] run function nyctocosm:mobs/nightmare_apparition/__generated__/block/22
execute as @s[tag=alerted] at @s run function nyctocosm:mobs/nightmare_apparition/__generated__/block/23
execute unless block ~ ~ ~ air run tp @s ~ ~0.05 ~
execute as @s[predicate=nyctocosm:in_light] at @s run tp @s ^ ^ ^-1.5