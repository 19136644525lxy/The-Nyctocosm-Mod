#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation xtraWeakness value = @s m.dream.weakness
scoreboard players operation xtraWeakness value += @s m.global.weakness
scoreboard players operation xtraDamage value = damageAmt value
scoreboard players operation xtraDamage value *= xtraWeakness value
scoreboard players operation 0 temp = xtraDamage value
scoreboard players operation 0 temp /= #100 const
scoreboard players operation xtraDamage value = 0 temp
scoreboard players operation xtraResistance value = @s m.dream.resistance
scoreboard players operation xtraResistance value += @s m.global.resistance
scoreboard players operation lessDamage value = damageAmt value
scoreboard players operation lessDamage value *= xtraResistance value
scoreboard players operation 0 temp = lessDamage value
scoreboard players operation 0 temp /= #100 const
scoreboard players operation lessDamage value = 0 temp
scoreboard players operation damageAmt value += xtraDamage value
scoreboard players operation damageAmt value -= lessDamage value
execute if score damageAmt value matches ..0 run scoreboard players set damageAmt value 0