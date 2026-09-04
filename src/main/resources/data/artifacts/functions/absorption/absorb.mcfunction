#built using mc-build (https://github.com/mc-build/mc-build)

function magic:calcstatsspell
scoreboard players operation 0 temp = @s m.global.power
scoreboard players operation 0 temp += @s m.void.power
scoreboard players operation 0 temp *= #2 const
scoreboard players operation @s ncd_heal = 0 temp
scoreboard players operation @s ncd_heal += a.absorption.healPerc a.value
scoreboard players operation @s ncd_heal *= @s m.damage_dealt.void
scoreboard players operation 0 temp = @s ncd_heal
scoreboard players operation 0 temp /= #100 const
scoreboard players operation @s ncd_heal = 0 temp
function ncdamage:apply_changes