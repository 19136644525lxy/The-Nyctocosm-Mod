#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add resonancehit
scoreboard players operation @s ncd_damage = Temp s.damage
scoreboard players operation 0 temp = @s ncd_damage
scoreboard players operation 0 temp *= #1000 const
scoreboard players operation @s ncd_damage = 0 temp
scoreboard players operation damageAmt value = @s ncd_damage
tag @a[tag=damagetemp,limit=1] add damager
execute as @s run function spells:resonance/__generated__/block/253