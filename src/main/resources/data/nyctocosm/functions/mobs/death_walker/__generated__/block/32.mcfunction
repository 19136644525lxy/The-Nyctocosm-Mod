#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[gamemode=survival,distance=..30,limit=1,sort=nearest] feet rotated ~ 0 run tp @s ~ ~ ~ ~ ~
function nyctocosm:mobs/death_walker/follow
execute if entity @a[gamemode=survival,distance=..7] run tag @s add attacking.trans
execute unless entity @a[gamemode=survival,distance=..30] run tag @s add idle.trans