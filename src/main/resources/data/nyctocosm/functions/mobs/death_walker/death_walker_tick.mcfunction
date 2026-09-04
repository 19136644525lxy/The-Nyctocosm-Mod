#built using mc-build (https://github.com/mc-build/mc-build)

function nyctocosm:id/match
execute unless entity @a[distance=..200] run function nyctocosm:mobs/death_walker/__generated__/block/26
execute at @s[tag=!following,tag=!attacking] if entity @a[gamemode=survival,distance=..30] run tag @s add following.trans
execute unless entity @e[tag=walker,tag=matchedid,tag=hitbox] run function nyctocosm:mobs/death_walker/remove
execute at @s as @e[tag=walker,tag=matchedid,tag=hitbox] run function nyctocosm:mobs/death_walker/__generated__/block/27
function nyctocosm:mobs/death_walker/state/switch
execute as @s[tag=idle] at @s run function nyctocosm:mobs/death_walker/__generated__/block/28
execute as @s[tag=walking] at @s run function nyctocosm:mobs/death_walker/__generated__/block/30
execute as @s[tag=following] at @s run function nyctocosm:mobs/death_walker/__generated__/block/32
execute as @s[tag=attacking] at @s run function nyctocosm:mobs/death_walker/__generated__/block/33
tag @e[tag=matchedid] remove matchedid