#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.shardhailtimer 1
execute at @a[distance=..80,gamemode=!spectator,sort=nearest] as @s[scores={ns.shardhailtimer=41}] run spreadplayers ~ ~ 0 15 false @s
execute at @s[scores={ns.shardhailtimer=41..}] run function sorcerer:attacks/shardhail/__generated__/block/22
effect give @a[distance=..1.4] slowness 10 1 true
execute as @s[scores={ns.shardhailtimer=41}] at @s positioned ~ ~30 ~ run function sorcerer:attacks/shardhail/summonhail
execute as @s[scores={ns.shardhailtimer=90..}] at @s run function sorcerer:attacks/shardhail/__generated__/block/23