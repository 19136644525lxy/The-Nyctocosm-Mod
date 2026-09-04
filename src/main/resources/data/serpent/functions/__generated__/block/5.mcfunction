#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a[tag=s.damagedSerpent] at @s run playsound nyctocosm:boss.destroy master @s ~ ~ ~
title @a[tag=s.damagedSerpent] times 20 100 20
title @a[tag=s.damagedSerpent] title {"translate":"entity.nyctocosm.boss_defeated","color":"gold","with":[{"translate":"entity.nyctocosm.boss_defeated1","color":"dark_red"}]}
advancement grant @s only nyctocosm:bosses/defeat_serpent
function lore:storylines/adherent/serpent