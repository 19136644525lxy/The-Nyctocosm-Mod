#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s ncd_damage = @a[tag=damagetemp,limit=1] m.dissonance.power
scoreboard players operation 0 temp = @s ncd_damage
scoreboard players operation 0 temp *= #3 const
scoreboard players operation @s ncd_damage = 0 temp
scoreboard players operation @s ncd_damage += a.expulsion.damageAmt a.value
scoreboard players operation 0 temp = @s ncd_damage
scoreboard players operation 0 temp *= #1000 const
scoreboard players operation @s ncd_damage = 0 temp
scoreboard players operation damageAmt value = @s ncd_damage
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function artifacts:expulsion/__generated__/block/33