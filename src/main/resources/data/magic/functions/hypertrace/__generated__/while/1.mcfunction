#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add m.global m.hypertrace.id 1
tag @e remove m.hypertraceIDMatch
execute as @e[tag=hypertrace] if score @s m.hypertrace.id = m.global m.hypertrace.id run tag @s add m.hypertraceIDMatch
scoreboard players set #WHILE_2 LANG_MC_INTERNAL 1
function magic:hypertrace/__generated__/while/0