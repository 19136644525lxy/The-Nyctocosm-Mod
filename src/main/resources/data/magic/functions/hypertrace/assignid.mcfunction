#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players set m.global m.hypertrace.id 0
execute as @e if score @s m.hypertrace.id matches 0 run tag @s add m.hypertraceIDMatch
function magic:hypertrace/__generated__/while/0
scoreboard players operation @s m.hypertrace.id = m.global m.hypertrace.id
tag @e remove m.hypertraceIDMatch