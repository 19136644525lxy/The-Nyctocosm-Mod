#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s run function magic:calcstatsdamage
execute as @s run function items:scythe/__generated__/block/26
execute store result storage admg:data damage.amount short 0.001 run scoreboard players get damageAmt value
execute as @s run function damage:api/voiddamage
scoreboard players operation @s m.damage_taken.void += damageAmt value
execute as @s if entity @s[type=player] run scoreboard players set IsPlayer value 1
execute if score IsPlayer value matches 1 run function items:scythe/__generated__/block/27
scoreboard players reset IsPlayer value