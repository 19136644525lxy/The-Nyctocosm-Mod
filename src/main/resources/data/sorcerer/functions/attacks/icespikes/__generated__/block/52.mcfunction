#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s positioned ~ ~5 ~ run function sorcerer:attacks/icespikes/tosurface
execute at @s run function sorcerer:attacks/icespikes/summonspike
scoreboard players set @s value 5