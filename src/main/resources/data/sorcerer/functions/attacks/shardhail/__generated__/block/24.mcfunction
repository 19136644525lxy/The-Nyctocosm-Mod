#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s[nbt={OnGround:1b}] ns.shardhailtimer 1
kill @s[scores={ns.shardhailtimer=5..}]
particle block ice ~ ~ ~ 0.1 0.1 0.1 0 2
execute as @s[nbt={OnGround:1b},scores={ns.shardhailtimer=2}] at @s run function sorcerer:attacks/shardhail/__generated__/block/25