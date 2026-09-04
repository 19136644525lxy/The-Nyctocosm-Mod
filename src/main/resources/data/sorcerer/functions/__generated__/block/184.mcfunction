#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[tag=ns.damagedsorcerer] at @s run playsound nyctocosm:boss.destroy master @s ~ ~ ~
title @a[tag=ns.damagedsorcerer] times 20 100 20
title @a[tag=ns.damagedsorcerer] title {"translate":"entity.nyctocosm.boss_defeated","color":"gold","with":[{"translate":"entity.nyctocosm.boss_defeated1","color":"dark_purple"}]}
execute as @a[tag=ns.damagedsorcerer] run function music:sorcerer/end
advancement grant @s only nyctocosm:bosses/defeat_sorcerer
function lore:storylines/adherent/sorcerer