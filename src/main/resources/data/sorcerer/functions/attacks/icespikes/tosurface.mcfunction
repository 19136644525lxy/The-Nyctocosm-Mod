#built using mc-build (https://github.com/mc-build/mc-build)

execute unless block ~ ~-1 ~ #nyctocosm:passthrough run tag @s add endscan
execute as @s[tag=!endscan] positioned ~ ~-1 ~ run function sorcerer:attacks/icespikes/tosurface
execute as @s[tag=endscan] run function sorcerer:attacks/icespikes/__generated__/block/60